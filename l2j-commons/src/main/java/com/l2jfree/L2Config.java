/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jfree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.LogManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.l2jfree.config.L2Properties;
import com.l2jfree.io.RedirectingOutputStream.BufferedRedirectingOutputStream;
import com.l2jfree.util.HandlerRegistry;

/**
 * @author evill33t
 */
public abstract class L2Config
{
	public static final String LOG_FILE = "./config/logging.properties";
	public static final String TELNET_FILE = "./config/telnet.properties";
	
	public static Level EXTENDED_LOG_LEVEL = Level.OFF;
	
	protected static final Log _log;
	
	public static final PrintStream out = System.out;
	public static final PrintStream err = System.err;
	
	static
	{
		if (System.getProperty("user.name").equals("root") && System.getProperty("user.home").equals("/root"))
		{
			System.out.print("L2Jfree servers should not run under root-account ... exited.");
			System.exit(-1);
		}
		
		final Map<String, List<String>> libs = new HashMap<String, List<String>>();
		
		final Set<File> files = new HashSet<File>();
		
		for (String classPath : System.getProperty("java.class.path").split(File.pathSeparator))
		{
			final File classPathFile = new File(classPath);
			
			if (classPathFile.isDirectory())
			{
				for (File f : classPathFile.listFiles())
					files.add(f);
			}
			else
				files.add(classPathFile);
		}
		
		boolean shouldExit = false;
		
		for (File f : files)
		{
			if (!f.getName().endsWith("jar"))
				continue;
			
			final StringBuilder sb = new StringBuilder();
			
			final StringTokenizer st = new StringTokenizer(f.getName(), "-");
			
			tokenizer: while (st.hasMoreTokens())
			{
				final String token = st.nextToken();
				
				boolean numberOnly = true;
				
				for (int i = 0; i < token.length(); i++)
				{
					char c = token.charAt(i);
					
					if (numberOnly && c == '.')
						break tokenizer;
					
					numberOnly &= Character.isDigit(c);
				}
				
				if (sb.length() != 0)
					sb.append("-");
				
				sb.append(token);
			}
			
			List<String> list = libs.get(sb.toString());
			
			if (list == null)
				libs.put(sb.toString(), list = new ArrayList<String>());
			else
				shouldExit = true;
			
			list.add(f.getName());
		}
		
		if (shouldExit)
		{
			System.out.println("Server should not run with classpath conflicts! "
					+ "(rename/remove possible conflicting classpath entries)");
			
			for (Map.Entry<String, List<String>> entry : libs.entrySet())
				if (entry.getValue().size() > 1)
					for (String name : entry.getValue())
						System.out.println("\t'" + name + "'");
			
			System.exit(-1);
		}
		
		System.setProperty("line.separator", "\r\n");
		System.setProperty("file.encoding", "UTF-8");
		System.setProperty("org.apache.commons.logging.LogFactory", "org.apache.commons.logging.impl.LogFactoryImpl");
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
		System.setProperty("java.util.logging.manager", "com.l2jfree.util.logging.L2LogManager");
		
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(LOG_FILE);
			
			LogManager.getLogManager().readConfiguration(fis);
		}
		catch (Exception e)
		{
			try
			{
				// if failed to load 'logging.properties', then load default logging parameters
				LogManager.getLogManager().readConfiguration();
			}
			catch (Exception e1)
			{
				throw new Error(e1);
			}
		}
		finally
		{
			IOUtils.closeQuietly(fis);
		}
		
		_log = LogFactory.getLog(L2Config.class);
		_log.info("logging initialized");
		
		System.setOut(new PrintStream(new BufferedRedirectingOutputStream() {
			@Override
			protected void handleLine(String line)
			{
				_log.info(line);
			}
		}));
		
		System.setErr(new PrintStream(new BufferedRedirectingOutputStream() {
			@Override
			protected void handleLine(String line)
			{
				_log.warn(line);
			}
		}));
	}
	
	protected L2Config()
	{
	}
	
	private static final HandlerRegistry<String, ConfigLoader> _loaders = new HandlerRegistry<String, ConfigLoader>(true) {
		@Override
		public String standardizeKey(String key)
		{
			return key.trim().toLowerCase();
		}
	};
	
	protected static void registerConfig(ConfigLoader loader)
	{
		_loaders.register(loader.getName(), loader);
	}
	
	public static void loadConfigs() throws Exception
	{
		for (ConfigLoader loader : _loaders.getHandlers().values())
			loader.load();
	}
	
	public static String loadConfig(String name) throws Exception
	{
		final ConfigLoader loader = _loaders.get(name);
		
		if (loader == null)
			throw new Exception();
		
		try
		{
			loader.load();
			return "'" + loader.getFileName() + "' reloaded!";
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
	}
	
	public static String getLoaderNames()
	{
		return StringUtils.join(_loaders.getHandlers().keySet().iterator(), "|");
	}
	
	protected static abstract class ConfigLoader
	{
		protected abstract String getName();
		
		protected String getFileName()
		{
			return "./config/" + getName().trim() + ".properties";
		}
		
		protected void load() throws Exception
		{
			_log.info("loading '" + getFileName() + "'");
			
			try
			{
				loadReader(new BufferedReader(new FileReader(getFileName())));
			}
			catch (Exception e)
			{
				_log.fatal("Failed to load '" + getFileName() + "'!", e);
				
				throw new Exception("Failed to load '" + getFileName() + "'!");
			}
		}
		
		protected void loadReader(BufferedReader reader) throws Exception
		{
			loadImpl(new L2Properties(reader));
		}
		
		protected void loadImpl(Properties properties) throws Exception
		{
		}
		
		@Override
		public int hashCode()
		{
			return getClass().hashCode();
		}
		
		@Override
		public boolean equals(Object obj)
		{
			return getClass().equals(obj.getClass());
		}
	}
}

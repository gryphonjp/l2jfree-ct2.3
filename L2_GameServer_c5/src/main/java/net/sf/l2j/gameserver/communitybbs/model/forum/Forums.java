package net.sf.l2j.gameserver.communitybbs.model.forum;

// Generated 19 f�vr. 2007 22:07:55 by Hibernate Tools 3.2.0.beta8

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Forums generated by hbm2java
 */
public class Forums implements java.io.Serializable
{

    // Fields    

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -8074717730201663809L;
    private int forumId;
    private String forumName;
    private int forumParent;
    private int forumPost;
    private int forumType;
    private int forumPerm;
    private int forumOwnerId;
    
    private Set<Topic> topics = new HashSet<Topic>(0);

    // Constructors

    /** default constructor */
    public Forums()
    {
    }
    /** 
     * minimal constructor 
     * Be carefull, forumId will be used only if strategies for the key is "assigned" in hbm.xml file
    */
    public Forums(int _forumId, String _forumName, int _forumParent, int _forumPost, int _forumType,
                    int _forumPerm, int _forumOwnerId)
    {
        this.forumId = _forumId;
        this.forumName = _forumName;
        this.forumParent = _forumParent;
        this.forumPost = _forumPost;
        this.forumType = _forumType;
        this.forumPerm = _forumPerm;
        this.forumOwnerId = _forumOwnerId;
    }

    /** 
     * full constructor 
     * Be carefull, forumId will be used only if strategies for the key is "assigned" in hbm.xml file
     */
    public Forums(int _forumId, String _forumName, int _forumParent, int _forumPost, int _forumType,
                  int _forumPerm, int _forumOwnerId, Set<Posts> _postses, Set<Topic> _topics)
    {
        this.forumId = _forumId;
        this.forumName = _forumName;
        this.forumParent = _forumParent;
        this.forumPost = _forumPost;
        this.forumType = _forumType;
        this.forumPerm = _forumPerm;
        this.forumOwnerId = _forumOwnerId;
        this.topics = _topics;
    }


    public Set<Topic> getTopics()
    {
        return this.topics;
    }

    public void setTopics(Set<Topic> _topics)
    {
        this.topics = _topics;
    }

    // Property accessors
    public int getForumId()
    {
        return this.forumId;
    }

    public void setForumId(int _forumId)
    {
        this.forumId = _forumId;
    }

    public String getForumName()
    {
        return this.forumName;
    }

    public void setForumName(String _forumName)
    {
        this.forumName = _forumName;
    }

    public int getForumParent()
    {
        return this.forumParent;
    }

    public void setForumParent(int _forumParent)
    {
        this.forumParent = _forumParent;
    }

    public int getForumPost()
    {
        return this.forumPost;
    }

    public void setForumPost(int _forumPost)
    {
        this.forumPost = _forumPost;
    }

    public int getForumType()
    {
        return this.forumType;
    }

    public void setForumType(int _forumType)
    {
        this.forumType = _forumType;
    }

    public int getForumPerm()
    {
        return this.forumPerm;
    }

    public void setForumPerm(int _forumPerm)
    {
        this.forumPerm = _forumPerm;
    }

    public int getForumOwnerId()
    {
        return this.forumOwnerId;
    }

    public void setForumOwnerId(int _forumOwnerId)
    {
        this.forumOwnerId = _forumOwnerId;
    }
    /**
     * @return true or false if the two objects are equals (not based on post id)
     * @param obj
     */
    public boolean equals(Object _obj) 
    {
        if (_obj == null) 
        {
            return false;
        }
        if (this == _obj) 
        {
            return true;
        }
        Forums rhs = (Forums) _obj;
        return new EqualsBuilder()
                        .appendSuper(super.equals(_obj))
                        .append(forumName, rhs.getForumName())
                        .append(forumOwnerId, rhs.getForumOwnerId())
                        .append(forumType, rhs.getForumType())
                        .append(forumPerm, rhs.getForumPerm())
                        .append(forumParent, rhs.getForumParent())
                        .isEquals();        
    }
    
    /**
     * @return the hashcode of the object
     */
    public int hashCode() 
    {
        return new HashCodeBuilder(17,37)
                        .append(this.forumName)
                        .append(this.forumOwnerId)
                        .append(this.forumType)
                        .append(this.forumPerm)
                        .append(this.forumParent)
                        .toHashCode();
    }    
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }    
}

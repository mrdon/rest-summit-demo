package com.atlassian.labs.restuser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user")
public class XmlUser
{

    private String name;

    public XmlUser()
    {
    }

    public XmlUser(String name)
    {
        this.name = name;
    }

    @XmlElement
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

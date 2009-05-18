package com.atlassian.labs.restuser;

import com.atlassian.jira.user.util.UserUtil;
import com.atlassian.jira.web.bean.UserBrowserFilter;
import com.opensymphony.user.User;
import com.opensymphony.user.ImmutableException;
import com.opensymphony.user.DuplicateEntityException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/users")
public class UserResource
{
    private final UserUtil userUtil;

    public UserResource(UserUtil userUtil)
    {
        this.userUtil = userUtil;
    }

    @GET
    public Collection<XmlUser> getAll() throws Exception
    {
        List<XmlUser> users = new ArrayList<XmlUser>();

        for (User user : new UserBrowserFilter(Locale.getDefault()).getFilteredUsers())
        {
            users.add(new XmlUser(user.getName()));
        }
        return users;
    }

    @GET
    @Path("{name}")
    public Response get(@PathParam("name") String name)
    {
        if (!userUtil.userExists(name))
        {
            return Response.status(404).build();
        }
        return Response.ok(new XmlUser(userUtil.getUser(name).getName())).build();
    }

    @DELETE
    @Path("{name}")
    public Response remove(@PathParam("name") String name)
    {
        User user = userUtil.getUser(name);
        userUtil.removeUser(user, user, null);
        return Response.ok().build();
    }

    @POST
    public Response create(@Context UriInfo uriInfo, XmlUser user) throws ImmutableException, DuplicateEntityException, URISyntaxException
    {
        userUtil.createUserNoEvent(user.getName(), user.getName(), user.getName(), user.getName());
        return Response.created(new URI(uriInfo.getAbsolutePath() + "/" + user.getName())).build();
    }
}
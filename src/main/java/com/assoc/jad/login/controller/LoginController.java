package com.assoc.jad.login.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.assoc.jad.login.dao.UserDao;
import com.assoc.jad.login.model.Users;
import com.assoc.jad.login.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController extends ALogin {

	@Autowired
	UserDao dao;
	@Autowired
	LoginService loginService;

	@RequestMapping("")
	public ModelAndView home1(HttpServletRequest req) {
		return home(req);
	}
	@RequestMapping("/")
	public ModelAndView home2(HttpServletResponse resp,HttpServletRequest req) {
		resp.setHeader("Access-Control-Allow-Origin",req.getHeader("origin") );
		return home(req);
	}
	@GetMapping("/home")
	public ModelAndView home(HttpServletRequest req) {
		
		String jsonitem = req.getParameter("caller");
		ModelAndView mv = new ModelAndView();
		req.getSession().setAttribute("caller", jsonitem);
		mv.addObject("caller",getJson(jsonitem));
		mv.setViewName("login.jsp");
		return mv;
	}
	/*
	 * POST
	 */
	@PostMapping("startloginAjax")
	public String startlogin(@RequestBody JSONObject json, HttpServletRequest req) {
		JSONObject obj = loginService.confirmCredentials(json,req);
		return obj.toString();
	}
	@PostMapping("confirmEmail")
	public String confirmEmail(@RequestBody JSONObject json, HttpServletRequest req) {
		loginService.confirmEmail(json);
		return json.toString();
	}
	@CrossOrigin
	@PostMapping("registrations")
	public String userRegistration(@RequestBody JSONObject json,HttpServletRequest req,HttpServletResponse resp) {

		json = loginService.addUser(json,req,resp);
		if (json.get("users") != null)
			if (!(Boolean)json.get("srvcompleted")) json = loginService.bldAndSendEmail(req,json);

		return json.toString();
	}
	@PostMapping("change/password")
	public String changePassword(@RequestBody JSONObject json, HttpServletRequest req) {
		JSONObject obj = loginService.changePassword(json,req);
		return obj.toString();
	}
	@PostMapping("forgot/password")
	public String forgotPassword(@RequestBody JSONObject json, HttpServletRequest req) {
		JSONObject obj = loginService.forgotPassword(json,req);
		return obj.toString();
	}
	@PostMapping("reset/password")
	public String resetPassword(@RequestBody JSONObject json, HttpServletRequest req) {
		JSONObject obj = loginService.resetPassword(json,req);
		return obj.toString();
	}
	@PostMapping("forgot/userid")
	public String forgotUserid(@RequestBody JSONObject json, HttpServletRequest req) {
		JSONObject obj = loginService.findUserid(json,req);
		return obj.toString();
	}
/*
 * old code might be useful.		
 */
	@GetMapping("registrations")
	public String postArtist(HttpServletResponse resp,HttpServletRequest req) {
		byte[] bytes = new byte[4096];
		StringBuilder sb = new StringBuilder();
		int ctr = 0;
        try {
           ServletInputStream sins = req.getInputStream();
           while (ctr != -1) {
        	   ctr = sins.read(bytes);
        	   if (ctr != -1) {
        		   String wrkstr = new String(bytes,0,ctr);
        		   sb.append(wrkstr);
        	   }
           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
		
		resp.setHeader("Access-Control-Allow-Origin","https://localhost" );
		return sb.toString();
	}
	
	@GetMapping("/users")
	@ResponseBody
	public CollectionModel<Users> getUsers() {
		
		System.out.println("in Userss");
		List<Users> Userss = dao.findAll();
		
	    for (Users Users : Userss) {
	        Link selfLink = linkTo(methodOn(this.getClass()).getUsers(Users.getId())).withSelfRel();
	        Link UserssLink = linkTo(methodOn(this.getClass()).getUsers()).withRel("users");
	        Users.add(selfLink);
	        Users.add(UserssLink);
	    }
		
	    CollectionModel<Users> result = CollectionModel.of(Userss);
		return result;
	}
	@GetMapping("/user/{id}")
	@ResponseBody
	public EntityModel<Users> getUsers(@PathVariable("id") int id) {
		
		System.out.println("in getUser");
		Users Users =  dao.findById(id);
		if (Users == null) Users = new Users();
		
		return EntityModel.of(Users,
			      linkTo(methodOn(this.getClass()).getUsers(id)).withSelfRel(),
			      linkTo(methodOn(this.getClass()).getUsers()).withRel("users"));
	}
	/*
	 * private methods
	 * 	public String startlogin(@RequestBody JSONObject json, HttpServletRequest req,@RequestParam("loginid") String loginid) {
	 */
	
}

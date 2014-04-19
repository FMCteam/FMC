package nju.software.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;
import nju.software.dataobject.Employee;
import nju.software.service.AccountService;
import nju.software.service.CustomerService;
import nju.software.service.EmployeeService;
import nju.software.util.Constants;
import nju.software.util.DateUtil;
import nju.software.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountController {

	@RequestMapping(value = "/account/employeeList.do")
	@Transactional(rollbackFor = Exception.class)
	public String employeeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		int page;
		int numberPerPage;
		String s_page = request.getParameter("page");
		String s_number = "10";
		if (s_page == null || s_page.equals("")) {
			s_page = "1";
		}
		// if (s_number == null || s_number.equals("")) {
		// s_number = "10";
		// }
		page = Integer.parseInt(s_page);
		numberPerPage = Integer.parseInt(s_number);
		System.out.println("page: " + page + " ,number: " + numberPerPage);
		List<Employee> list = employeeService.getEmployeeByPage(page,
				numberPerPage);
		int pageNumber = (employeeService.getcount() - 1) / numberPerPage + 1;
		model.addAttribute("employee_list", list);
		model.addAttribute("page_number", pageNumber);
		model.addAttribute("page", page);

		return "/account/employeeList";
	}

	@RequestMapping(value = "/account/addEmployeeDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String addEmployeeDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return "/account/addEmployeeDetail";
	}

	@RequestMapping(value = "/account/addEmployeeSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String addEmployeeSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String username = request.getParameter("employee_id");
		String employeeName = request.getParameter("employee_name");
		Short employeeSex = Short
				.parseShort(request.getParameter("radiofield"));
		Short employeeAge = Short.parseShort(request
				.getParameter("employee_age"));
		String department = request.getParameter("department");
		String employeeRole = request.getParameter("role");
		String inTime = request.getParameter("in_date");
		Timestamp entryTime = null;
		if (inTime != null && inTime != "") {
			Date inDate = DateUtil.parse(inTime, DateUtil.newFormat);
			entryTime = new Timestamp(inDate.getTime());
		}
		int directLeader = -1;
		String employeeLevel = "-1";
		String phone1 = request.getParameter("phone1");
		String phone2 = request.getParameter("phone2");
		String outTime = request.getParameter("out_date");
		Timestamp leaveTime = null;
		if (outTime != null && outTime != "") {
			Date outDate = DateUtil.parse(outTime, DateUtil.newFormat);
			leaveTime = new Timestamp(outDate.getTime());
		}
		String employeeState = request.getParameter("state");
		String exCompany = request.getParameter("pre_company");
		String exBusiness = request.getParameter("pre_job");
		String exJob = request.getParameter("pre_role");
		String eduBackground = request.getParameter("edu_background");
		String eduSchool = request.getParameter("school");
		String eduField = request.getParameter("major");
		String hometown = request.getParameter("hometown");
		String address = request.getParameter("address");
		String chinaId = request.getParameter("id_card");

		Account account = accountService.getAccountByUsername(username);
		boolean success = false;
		if (account == null) {
			// 用户名不存在，可以创建
			if ((employeeName != null)
					&& (employeeSex == 1 || employeeSex == 2 || employeeSex == 3)
					&& (employeeAge > 0) && (department != null)
					&& (entryTime != null) && (employeeState != null)
					&& (chinaId != null)) {
				Employee employee = new Employee(employeeName, employeeSex,
						employeeAge, department, entryTime, directLeader,
						employeeLevel, employeeState, chinaId);
				employee.setPhone1(phone1);
				employee.setPhone2(phone2);
				employee.setLeaveTime(leaveTime);
				employee.setExCompany(exCompany);
				employee.setExBusiness(exBusiness);
				employee.setExJob(exJob);
				employee.setEduBackground(eduBackground);
				employee.setEduSchool(eduSchool);
				employee.setEduField(eduField);
				employee.setHometown(hometown);
				employee.setAddress(address);
				int employeeId = employeeService.addEmployee(employee);
				String userPassword = SecurityUtil.md5hex("123456");
				String userType = "EMPLOYEE";
				String userRole = employeeRole;
				account = new Account(employeeId, userType, userPassword,
						username, userRole, employeeName);
				accountService.addAccount(account);
				success = true;
			}
		} else {
			success = false;
		}

		if (success) {
			return "forward:/account/employeeList.do";
		} else {
			return "forward:/account/addEmployeeDetail.do";
		}
	}

	@RequestMapping(value = "/account/modifyEmployeeDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String modifyEmployeeDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		int employeeId = account.getUserId();
		Employee employee = employeeService.getEmployeeById(employeeId);
		session.setAttribute("cur_employee", employee);

		// 将要修改的用户放入model中
		Employee modifyEmployee = null;
		Account modifyAccount = null;
		String id = request.getParameter("id");
		if (id == null || id.equals("")) {
			modifyEmployee = employee;
			modifyAccount = account;
		} else {
			int modifyEmployeeId = Integer.parseInt(id);
			modifyEmployee = employeeService.getEmployeeById(modifyEmployeeId);
			String role = "EMPLOYEE";
			modifyAccount = accountService.getAccoutByUserIdAndUserRole(role,
					modifyEmployeeId);
		}
		model.addAttribute("account_to_modify", modifyAccount);
		model.addAttribute("employee_to_modify", modifyEmployee);
		System.out.println("employee modify");
		return "/account/modifyEmployeeDetail";
	}

	@RequestMapping(value = "/account/modifyEmployeeSubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String modifyEmployeeSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		HttpSession session = request.getSession();
		String username = request.getParameter("employee_id");
		System.out.println("username: " + username);
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		String employeeName = request.getParameter("employee_name");
		Short employeeSex = Short
				.parseShort(request.getParameter("radiofield"));
		Short employeeAge = Short.parseShort(request
				.getParameter("employee_age"));
		String department = request.getParameter("department");
		String employeeRole = request.getParameter("role");
		// System.out.println(request.getParameter("in_date"));
		String inTime = request.getParameter("in_date");
		Date inDate = DateUtil.parse(inTime, DateUtil.newFormat);
		Timestamp entryTime = new Timestamp(inDate.getTime());
		int directLeader = -1;
		String employeeLevel = "-1";
		String phone1 = request.getParameter("phone1");
		String phone2 = request.getParameter("phone2");
		String outTime = request.getParameter("out_date");
		Timestamp leaveTime = null;
		// System.out.println("outDate: " + request.getParameter("out_date"));
		if (outTime != null && outTime != "") {
			Date outDate = DateUtil.parse(outTime, DateUtil.newFormat);
			leaveTime = new Timestamp(outDate.getTime());
		}
		String employeeState = request.getParameter("state");
		String exCompany = request.getParameter("pre_company");
		String exBusiness = request.getParameter("pre_job");
		String exJob = request.getParameter("pre_role");
		String eduBackground = request.getParameter("edu_background");
		String eduSchool = request.getParameter("school");
		String eduField = request.getParameter("major");
		String hometown = request.getParameter("hometown");
		String address = request.getParameter("address");
		String chinaId = request.getParameter("id_card");

		// 两次输入密码不相同
		if (!password1.equals(password2)) {
			request.setAttribute("msg", "两次输入密码不相同！");
			// session.setAttribute("msg", "两次输入密码不相同！");
			System.out.println("password");
			return "forward:/account/modifyEmployeeDetail.do";
		}

		boolean success = false;
		Account account = (Account) session.getAttribute("cur_user");
		// Account account = accountService.getAccountByUsername(username);
		if (account == null) {
			// 用户不存在，无法修改
			request.setAttribute("msg", "用户不存在！");
			// session.setAttribute("msg", "用户不存在！");
			System.out.println("no user");
			success = false;
		} else if (!account.getUserName().equals(username)
				&& !account.getUserRole().equals("ADMIN")) {
			// 当前用户不是管理员，且修改的不是当前用户，无法修改
			request.setAttribute("msg", "用户名与当前用户不同！");
			// session.setAttribute("msg", "用户不存在！");
			System.out.println("wrong username");
			success = false;
		} else {
			// 用户存在，或者用户为管理员，可以修改
			int employeeId = account.getUserId();
			Account accountToModify = account;
			if (account.getUserRole().equals("ADMIN")) {
				accountToModify = accountService.getAccountByUsername(username);
				employeeId = accountToModify.getUserId();
			}

			Employee employee = employeeService.getEmployeeById(employeeId);
			employee.setEmployeeName(employeeName);
			employee.setSex(employeeSex);
			employee.setAge(employeeAge);
			employee.setDepartment(department);
			employee.setEntryTime(entryTime);
			employee.setDirectLeader(directLeader);
			employee.setEmployeeLevel(employeeLevel);
			employee.setPhone1(phone1);
			employee.setPhone2(phone2);
			employee.setLeaveTime(leaveTime);
			employee.setEmployeeState(employeeState);
			employee.setExCompany(exCompany);
			employee.setExBusiness(exBusiness);
			employee.setExJob(exJob);
			employee.setEduBackground(eduBackground);
			employee.setEduSchool(eduSchool);
			employee.setEduField(eduField);
			employee.setHometown(hometown);
			employee.setAddress(address);
			employee.setChinaId(chinaId);
			employeeService.updateEmployee(employee);

			if (!accountToModify.getUserRole().equals("ADMIN")) {
				String userType = accountToModify.getUserType();
				String userRole = employeeRole;
				accountToModify.setUserRole(userRole);
				accountToModify.setUserType(userType);
			}
			String nickName = employeeName;
			accountToModify.setNickName(nickName);

			if ((password1 != null) && (!password1.equals(""))) {
				String userPassword = SecurityUtil.md5hex(password1);
				accountToModify.setUserPassword(userPassword);
			}
			accountService.updateAccount(accountToModify);

			success = true;
		}

		if (success) {
			return "forward:/account/employeeList.do";
		} else {
			return "forward:/account/modifyEmployeeDetail.do";
		}
	}

	/**
	 * 修改账户链接跳转
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/account/deleteEmployeeSubmit.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String deleteEmployeeSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		boolean success = false;
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		if (account.getUserRole().equals("ADMIN")) {
			String id = request.getParameter("id");
			int employeeId = Integer.parseInt(id);
			Employee employee = employeeService.getEmployeeById(employeeId);
			Account accountToDelete = null;
			if (employee != null) {
				String role = "EMPLOYEE";
				accountToDelete = accountService.getAccoutByUserIdAndUserRole(
						role, employeeId);
				if (accountToDelete != null) {
					int accountId = accountToDelete.getAccountId();
					employeeService.deleteEmployee(employeeId);
					accountService.deleteAccount(accountId);
					success = true;
				}
			}
		}

		if (success) {
			System.out.println("employee delete success");
			return "forward:/account/employeeList.do";
		} else {
			System.out.println("employee delete failed");
			return "forward:/account/employeeList.do";
		}
	}

	@RequestMapping(value = "/account/customerList.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String customerList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String s_page = request.getParameter("page");
		String s_number = request.getParameter("number_per_page");
		System.out.println("page: " + s_page);
		System.out.println("number: " + s_number);
		if (s_page == null || s_page.equals("")) {
			s_page = "1";
		}
		if (s_number == null || s_number.equals("")) {
			s_number = "10";
		}
		System.out.println("page: " + s_page);
		System.out.println("number: " + s_number);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", Integer.parseInt(s_page));
		m.put("number_per_page", Integer.parseInt(s_number));
		List<Object> o = this.customerService.listCustomer(m);
		model.addAttribute("customer_list", o.get(0));
		model.addAttribute("page_number",
				((HashMap<String, Object>) o.get(1)).get("page_number"));
		model.addAttribute("page",
				((HashMap<String, Object>) o.get(1)).get("page"));
		return "/account/customerList";
	}

	@RequestMapping(value = "/account/addCustomerDetail.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String addCustomerDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		System.out.println("customer add");
		return "/account/addCustomerDetail";

	}

	@RequestMapping(value = "/account/addCustomerSubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String addCustomerSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String companyId = request.getParameter("company_id");
		String companyName = request.getParameter("company_name");
		String customerName = request.getParameter("customer_name");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String websiteUrl = request.getParameter("website_url");
		String websiteType = request.getParameter("website_type");
		String companyAddress = request.getParameter("company_address");
		String companyFax = request.getParameter("company_fax");
		String companyPhone = request.getParameter("company_phone");
		String buyContact = request.getParameter("buy_constact");
		String contactPhone1 = request.getParameter("contact_phone_1");
		String contactPhone2 = request.getParameter("contact_phone_2");
		String qq = request.getParameter("qq");
		String email = request.getParameter("email");
		String customerPhone = request.getParameter("customer_phone");
		String bossName = request.getParameter("boss_name");
		String bossPhone = request.getParameter("boss_phone");

		String userName = request.getParameter("user_name");
		String userPassword = request.getParameter("user_password");

		System.out.println(request.getParameter("register_date"));
		Date d = DateUtil.parse(request.getParameter("register_date"),
				DateUtil.newFormat);

		Timestamp registerDate = new Timestamp(d.getTime());

		HttpSession session = request.getSession();
		Account registerId = (Account) session.getAttribute("cur_user");
		boolean exist = accountService.checkExit(userName);
		if (exist) {

			model.addAttribute("exist", true);
			model.addAttribute("success", false);
			return "redirct:/customer/add.do";
		} else {
			Customer c = new Customer();

			c.setCompanyId(companyId);
			c.setCompanyName(companyName);
			c.setCustomerName(customerName);
			c.setProvince(province);
			c.setCity(city);
			c.setWebsiteUrl(websiteUrl);
			c.setWebsiteType(websiteType);
			c.setCompanyAddress(companyAddress);
			c.setCompanyFax(companyFax);
			c.setCompanyPhone(companyPhone);
			c.setBuyContact(buyContact);
			c.setContactPhone1(contactPhone1);
			c.setContactPhone2(contactPhone2);
			c.setQq(qq);
			c.setEmail(email);
			c.setCustomerPhone(customerPhone);
			c.setBossName(bossName);
			c.setBossPhone(bossPhone);
			c.setRegisterDate(registerDate);
			c.setRegisterEmployeeId(registerId.getUserId());
			Account account = new Account();

			boolean test = this.customerService.addCustomer(c, userName,
					userPassword);

			if (test) {

				model.addAttribute("exist", false);
				model.addAttribute("success", true);
				return "redirect:/account/customerList.do";
			} else {
				model.addAttribute("exist", false);
				model.addAttribute("success", false);
				return "redirect:/account/customerList.do";
			}
		}
	}

	@RequestMapping(value = "/account/modifyCustomerDetail.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String modifyCustomerDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String customerId = request.getParameter("customer_id");
		int id = 0;
		try {
			id = Integer.parseInt(customerId);
			Customer c = customerService.findByCustomerId(id);
			if (c == null) {
				System.out.println("c is null");
			}
			Account account = accountService.getAccoutByUserIdAndUserRole(
					Constants.USER_TYPE_CUSTOMER, c.getCustomerId());
			System.out.println(account.getNickName());
			model.addAttribute("exist", true);
			//
			model.addAttribute("customer_to_modify", c);
			//
			model.addAttribute("account_to_modify", account);
			System.out.println("customer modify");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/account/modifyCustomerDetail";
	}

	@RequestMapping(value = "/account/modifyCustomerSubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String modifyCustomerSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String customerId = request.getParameter("customer_id");
		String companyId = request.getParameter("company_id");
		String companyName = request.getParameter("company_name");
		String customerName = request.getParameter("customer_name");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String websiteUrl = request.getParameter("website_url");
		String websiteType = request.getParameter("website_type");
		String companyAddress = request.getParameter("company_address");
		String companyFax = request.getParameter("company_fax");
		String companyPhone = request.getParameter("company_phone");
		String buyContact = request.getParameter("buy_constact");
		String contactPhone1 = request.getParameter("contact_phone_1");
		String contactPhone2 = request.getParameter("contact_phone_2");
		String qq = request.getParameter("qq");
		String email = request.getParameter("email");
		String customerPhone = request.getParameter("customer_phone");
		String bossName = request.getParameter("boss_name");
		String bossPhone = request.getParameter("boss_phone");
		String employeId = request.getParameter("register_employee_id");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		if (password1 != null && password2 != null
				&& (!password1.equals(password2))) {
			model.addAttribute("msg", "两次密码不相同");
			return "customer/doModify.do";
		}
		Customer c = customerService.findByCustomerId(Integer
				.parseInt(customerId));

		c.setCompanyId(companyId);
		c.setCompanyName(companyName);
		c.setCustomerName(customerName);
		c.setProvince(province);
		c.setCity(city);
		c.setWebsiteUrl(websiteUrl);
		c.setWebsiteType(websiteType);
		c.setCompanyAddress(companyAddress);
		c.setCompanyFax(companyFax);
		c.setCompanyPhone(companyPhone);
		c.setBuyContact(buyContact);
		c.setContactPhone1(contactPhone1);
		c.setContactPhone2(contactPhone2);
		c.setQq(qq);
		c.setEmail(email);
		c.setCustomerPhone(customerPhone);
		c.setBossName(bossName);
		c.setBossPhone(bossPhone);
		boolean success1 = this.customerService.saveCustomer(c);
		boolean success2 = false;
		// save account operation
		if (password1 != null && password2 != null
				&& password1.equals(password2)) {
			success2 = this.accountService.saveAccount(c, password1,
					customerName);
		}

		if (success1 && success2) {
			System.out.println("customer modify successfully");
			return "redirect:/account/customerList.do";
		} else {
			model.put("customer_id", customerId);
			return "redirect:/account/customerList.do";
		}
	}

	@RequestMapping(value = "/account/deleteCustomerSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String deleteCustomerSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String customer_id = request.getParameter("customer_id");
		boolean sucess = customerService.deleteCustomerById(Integer
				.parseInt(customer_id));
		System.out.println("customer delete successfully" + sucess);
		return "forward:/account/customerList.do";
	}
	
	@RequestMapping(value = "customer/viewCustomer.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String viewCustomer(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String customerId = request.getParameter("customer_id");
		int id = Integer.parseInt(customerId);
		Customer c = this.customerService.findByCustomerId(id);

		model.addAttribute("customer", c);
		return "account/show_customer";
	}

	public String test = "test";
	@Autowired
	private AccountService accountService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CustomerService customerService;
}

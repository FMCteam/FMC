package nju.software.controller.mobile;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;

import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;
import nju.software.dataobject.Employee;
import nju.software.service.AccountService;
import nju.software.service.CommonService;
import nju.software.service.CustomerService;
import nju.software.service.EmployeeService;
import nju.software.util.Constants;
import nju.software.util.DateUtil;
import nju.software.util.JSONUtil;
import nju.software.util.SecurityUtil;
import nju.software.util.SessionUtil;
import nju.software.util.mail.*;

import org.drools.core.util.StringUtils;
import org.drools.lang.DRLExpressions.literal_return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountMobileController {
	private final static String IS_SUCCESS = "isSuccess";

		@RequestMapping(value = "/account/mobile_employeeList.do")
		public void employeeList(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {
			int page;
			int numberPerPage = 10;
			String s_page = request.getParameter("page");
			if (s_page == null || s_page.equals("")) {
				s_page = "1";
			}
			page = Integer.parseInt(s_page);
			System.out.println("page: " + page + " ,number: " + numberPerPage);
			List<Employee> list = employeeService.getEmployeeByPage(page,
					numberPerPage);
			int pageNumber = (employeeService.getcount() - 1) / numberPerPage + 1;
			model.addAttribute("employee_list", list);
			model.addAttribute("page_number", pageNumber);
			model.addAttribute("page", page);
			jsonUtil.sendJson(response, model);
		}


		@RequestMapping(value = "/account/mobile_addEmployeeSubmit.do")
		public void addEmployeeSubmit(HttpServletRequest request,
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
			String jobphone = request.getParameter("jobphone");
			String email = request.getParameter("email");
			String qq = request.getParameter("qq");
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
					employee.setJobPhone(jobphone);
					employee.setEmail(email);
					employee.setQq(qq);
					int employeeId = employeeService.addEmployee(employee);
					String userPassword = SecurityUtil.md5hex("123456");
					String userType = "EMPLOYEE";
					String userRole = employeeRole;
					account = new Account(employeeId, userType, userPassword,
							username,  userRole, employeeName);
					int accountId =accountService.addAccount(account);
					System.out.println("=================accountId:"+accountId);
					accountService.addAccountRole(userRole, accountId);
					success = true;
				}
			} else {
				success = false;
			}

			if (success) {
				model.addAttribute("notify", "添加员工成功！");
			} else {
				model.addAttribute("notify", "添加员工失败！");
			}
			model.addAttribute(IS_SUCCESS, success);
			jsonUtil.sendJson(response, model);
		}

		@RequestMapping(value = "/account/mobile_modifyEmployeeDetail.do")
		public void modifyEmployeeDetail(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {
			Account account = commonService.getCurAccount(request, response);
			if (account == null) {
				return;
			}
			int employeeId = account.getUserId();
			Employee employee = employeeService.getEmployeeById(employeeId);
			String jsessionId = request.getParameter(Constants.PARAM_SESSION_ID);
			HttpSession session = SessionUtil.getSession(request, jsessionId);
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
			jsonUtil.sendJson(response, model);
		}

		@RequestMapping(value = "/account/mobile_modifyEmployeeSubmit.do", method = RequestMethod.POST)
		public void modifyEmployeeSubmit(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {
			Account account = commonService.getCurAccount(request, response);
			if (account == null) {
				return;
			}
			String username = request.getParameter("employee_id");
			String password1 = request.getParameter("password1");
			String password2 = request.getParameter("password2");
			String employeeName = request.getParameter("employee_name");
			Short employeeSex = Short
					.parseShort(request.getParameter("radiofield"));
			Short employeeAge = Short.parseShort(request
					.getParameter("employee_age"));
			String department = request.getParameter("department");
			String employeeRole = request.getParameter("role");
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
			String jobphone = request.getParameter("jobphone");
			String email = request.getParameter("email");
			String qq = request.getParameter("qq");

			boolean success = false;
			// Account account = accountService.getAccountByUsername(username);
			if (!account.getUserName().equals(username)
					&& !account.getUserRole().equals("ADMIN")) {
				// 当前用户不是管理员，且修改的不是当前用户，无法修改
				model.addAttribute("notify", "用户名与当前用户不同！");
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
				employee.setJobPhone(jobphone);
				employee.setEmail(email);
				employee.setQq(qq);

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
				System.out.println("============employeeRole:"+employeeRole);
				accountService.updateAccountRole(accountToModify.getAccountId(),employeeRole );
				model.addAttribute("notify", "修改成功！");
				success = true;
			}
			model.addAttribute(IS_SUCCESS, success);
			jsonUtil.sendJson(response, model);
		}

		/**
		 * 发送找回密码邮件
		 * 
		 * @param request
		 * @param response
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "/account/mobile_sendResetPassMail.do", method = RequestMethod.POST)
		public void sendResetPassMail(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {
			System.out.println("---------------开始发送邮件----------------");
			String username = request.getParameter("username");
			Account account = accountService.getAccountByUsername(username);

			boolean isSuccess = false; 
			if (null == account) { // 登录名不存在
				model.addAttribute("notify", "登录名不存在");
				model.addAttribute(IS_SUCCESS, isSuccess);
				jsonUtil.sendJson(response, model);
				return;
			}

			Customer customer = customerService.findByCustomerId(account
					.getUserId());
			if (null != customer) {
				try {
					String secretKey = UUID.randomUUID().toString(); // 密钥
					Timestamp failTime = new Timestamp(
							System.currentTimeMillis() + 30 * 60 * 1000); // 30分钟后过期
					long date = failTime.getTime() / 1000 * 1000; // 忽略毫秒数
					account.setValidataCode(secretKey);
					account.setResetLinkFailTime(failTime);
					accountService.updateAccount(account); // 保存到数据库
					String key = account.getUserName() + "$" + date + "$"
							+ secretKey;
					String digitalSignature = SecurityUtil.md5hex(key); // 数字签名

					String emailTitle = "智造链 - 找回您的密码";
					String path = request.getContextPath();
					String basePath = request.getScheme() + "://"
							+ request.getServerName() + ":"
							+ request.getServerPort() + path + "/";
					String resetPassHref = basePath
							+ "account/checkResetPassLink.do?sid="
							+ digitalSignature + "&userName="
							+ account.getUserName();
					String emailContent = "请勿回复本邮件。点击下面的链接，重设密码<br/><a href="
							+ resetPassHref + " target='_BLANK'>点击我重新设置密码</a>"
							+ "<br/>提示：本邮件超过30分钟，链接将会失效，需要重新申请";
					System.out.println(resetPassHref);
					MailSenderInfo mailSenderInfo = new MailSenderInfo();
					mailSenderInfo.setSubject(emailTitle);
					mailSenderInfo.setContent(emailContent);
					mailSenderInfo.setToAddress(customer.getEmail());
					SimpleMailSender.sendHtmlMail(mailSenderInfo);

					isSuccess = true;
					model.addAttribute("notify", "操作成功，已经发送找回密码链接到您邮箱。请在30分钟内重置密码");
				} catch (Exception e) {
					e.printStackTrace();
					isSuccess = false;
					model.addAttribute("notify", "邮箱不存在？未知错误，请联系管理员。");
				}
			}
			System.out.println("---------------邮件发送完成----------------");
			model.addAttribute(IS_SUCCESS, isSuccess);
			jsonUtil.sendJson(response, model);
		}

		/**
		 * 验证重设密码链接
		 * 
		 * @param request
		 * @param response
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "/account/mobile_checkResetPassLink.do", method = RequestMethod.GET)
		public void checkResetLink(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {
			String sid = request.getParameter("sid");
			String userName = request.getParameter("userName");
			boolean isSuccess = false;
			if (sid.equals("") || userName.equals("")) {
				model.addAttribute("notify", "链接不完整，请重新生成");
				model.addAttribute(IS_SUCCESS, isSuccess);
				jsonUtil.sendJson(response, model);
				return;
			}

			Account account = accountService.getAccountByUsername(userName);
			if (null == account) {
				model.addAttribute("notify", "链接错误，无法找到匹配用户，请重新申请找回密码");
				model.addAttribute(IS_SUCCESS, isSuccess);
				jsonUtil.sendJson(response, model);
				return;
			}
			Timestamp failTime = account.getResetLinkFailTime();
			if (failTime.getTime() <= System.currentTimeMillis()) { // 表示已经过期
				model.addAttribute("notify", "链接已经过期，请重新申请找回密码");
				model.addAttribute(IS_SUCCESS, isSuccess);
				jsonUtil.sendJson(response, model);
				return;
			}
			String key = account.getUserName() + "$" + failTime.getTime() / 1000
					* 1000 + "$" + account.getValidataCode(); // 数字签名
			String digitalSignature = SecurityUtil.md5hex(key);
			System.out.println(key + "\t" + digitalSignature);
			if (!digitalSignature.equals(sid)) {
				model.addAttribute("notify", "链接不正确，是否已经过期了？请重新申请");
				model.addAttribute(IS_SUCCESS, isSuccess);
				jsonUtil.sendJson(response, model);
				return;
			}

			request.getSession().setAttribute(Constants.PARAM_CUR_ACCOUNT, account);
			isSuccess = true;
			model.addAttribute(IS_SUCCESS, isSuccess);
			jsonUtil.sendJson(response, model);
		}

		/**
		 * 重置密码
		 * 
		 * @param request
		 * @param response
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "/account/mobile_resetPassword.do", method = RequestMethod.POST)
		public void resetPassword(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {
			Account account = commonService.getCurAccount(request, response);
			if (account == null) {
				return;
			}
			boolean isSuccess = false;
			if (null != account) {
				String password1 = request.getParameter("new_pass");
				String password2 = request.getParameter("cfm_new_pass");

				if (StringUtils.isEmpty(password1)
						|| StringUtils.isEmpty(password2)
						|| !password1.equals(password2)) {
					model.addAttribute(IS_SUCCESS, isSuccess);
					jsonUtil.sendJson(response, model);
					return;
				} else {
					account.setUserPassword(SecurityUtil.md5hex(password1));
					accountService.updateAccount(account);
					isSuccess = true;
					model.addAttribute(IS_SUCCESS, isSuccess);
					jsonUtil.sendJson(response, model);
					return;
				}
			} else {
				isSuccess = false;
				model.addAttribute(IS_SUCCESS, isSuccess);
				jsonUtil.sendJson(response, model);
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
		@RequestMapping(value = "/account/mobile_deleteEmployeeSubmit.do", method = RequestMethod.GET)
		public void deleteEmployeeSubmit(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {
			boolean success = false;
			Account account = commonService.getCurAccount(request, response);
			if (account == null) {
				return;
			}
			if (account.getUserRole().equals("ADMIN")) {
				String id = request.getParameter("id");
				int employeeId = Integer.parseInt(id);
				Employee employee = employeeService.getEmployeeById(employeeId);
				Account accountToDelete = null;
				if (employee != null) {
					String role = "EMPLOYEE";
					accountToDelete = accountService.getAccoutByUserIdAndUserRole(
							role, employeeId);
					System.out.println("============employeeId:"+employeeId);
					if (accountToDelete != null) {
						int accountId = accountToDelete.getAccountId();
						employeeService.deleteEmployee(employeeId);
						accountService.deleteAccount(accountId);
						accountService.deleteAccountRole(accountId);
						success = true;
					}
				}
			}

			if (success) {
				model.addAttribute("notify", "删除成功！");
				System.out.println("employee delete success");
				model.addAttribute(IS_SUCCESS, success);
			} else {
				model.addAttribute("notify", "删除失败！");
				System.out.println("employee delete failed");
				model.addAttribute(IS_SUCCESS, !success);
			}
			jsonUtil.sendJson(response, model);
		}

		@RequestMapping(value = "/account/mobile_customerList.do", method = RequestMethod.GET)
		public void customerList(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {
			int page;
			int numberPerPage = 10;
			String s_page = request.getParameter("page");
			if (s_page == null || s_page.equals("")) {
				s_page = "1";
			}
			// String s_number = request.getParameter("number_per_page");
			/*
			 * if (s_page == null || s_page.equals("")) { s_page = "1"; } if
			 * (s_number == null || s_number.equals("")) { s_number = "10"; }
			 */
			// int pageNumber = (employeeService.getcount() - 1) / numberPerPage +
			// 1;
			System.out.println("page: " + s_page);
			// System.out.println("number: " + s_number);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("page", Integer.parseInt(s_page));
			m.put("number_per_page", numberPerPage);
			List<Object> o = this.customerService.listCustomer(m);
			model.addAttribute("customer_list", o.get(0));
			model.addAttribute("page_number",
					((HashMap<String, Object>) o.get(1)).get("page_number"));
			model.addAttribute("page", s_page);
			model.addAttribute("notify", "顾客列表");
			jsonUtil.sendJson(response, model);
		}

		@RequestMapping(value = "/account/mobile_addCustomerSubmit.do", method = RequestMethod.POST)
		public void addCustomerSubmit(HttpServletRequest request,
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

			boolean exist = accountService.checkExit(userName);
			if (exist) {
				model.addAttribute("exist", true);
				model.addAttribute(IS_SUCCESS, false);
				jsonUtil.sendJson(response, model);
				return;
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
				Account registerId = commonService.getCurAccount(request, response);
				if (registerId == null) {
					return;
				}
				if (registerId != null) {
					c.setRegisterEmployeeId(registerId.getUserId());
					Account account = new Account();

					boolean test = this.customerService.addCustomer(c, userName,
							userPassword);
					
					Account account_Customer =accountService.getAccoutByUserIdAndUserRole("CUSTOMER", c.getCustomerId());
					System.out.println("customer:"+account_Customer.getAccountId());
					accountService.addAccountRole("CUSTOMER", account_Customer.getAccountId());
					if (test) {

						model.addAttribute("exist", false);
						model.addAttribute(IS_SUCCESS, true);
						jsonUtil.sendJson(response, model);
						return;
					} else {
						model.addAttribute("exist", false);
						model.addAttribute(IS_SUCCESS, false);
						jsonUtil.sendJson(response, model);
						return;
					}
				} else {
					c.setRegisterEmployeeId(0);

					boolean test = this.customerService.addCustomer(c, userName,
							userPassword);
					Account account_Customer =accountService.getAccoutByUserIdAndUserRole("CUSTOMER", c.getCustomerId());
					System.out.println("customer:"+account_Customer.getAccountId());
					accountService.addAccountRole("CUSTOMER", account_Customer.getAccountId());
				
				//	accountService.addAccountRole("CUSTOMER", c.g);
					if (test) {

						model.addAttribute("exist", false);
						model.addAttribute(IS_SUCCESS, true);
					} else {
						model.addAttribute("exist", false);
						model.addAttribute(IS_SUCCESS, false);
					}
				}
			}
			jsonUtil.sendJson(response, model);
		}

		@RequestMapping(value = "/account/mobile_modifyCustomerDetail.do", method = RequestMethod.GET)
		public void modifyCustomerDetail(HttpServletRequest request,
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
			jsonUtil.sendJson(response, model);
		}

		@RequestMapping(value = "/account/mobile_modifyCustomerSubmit.do", method = RequestMethod.POST)
		public void modifyCustomerSubmit(HttpServletRequest request,
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
			String password1 = request.getParameter("password");
			
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
			
			success2 = this.accountService.saveAccount(c, password1,
						customerName);

			if (success1 && success2) {
				model.addAttribute(IS_SUCCESS, true);
			} else {
				model.addAttribute(IS_SUCCESS, false);
			}
			jsonUtil.sendJson(response, model);
		}

		@RequestMapping(value = "/account/mobile_deleteCustomerSubmit.do")
		public void deleteCustomerSubmit(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {

			String customer_id = request.getParameter("customer_id");
			boolean sucess = customerService.deleteCustomerById(Integer
					.parseInt(customer_id));
			Account account =accountService.getAccoutByUserIdAndUserRole("CUSTOMER", Integer
					.parseInt(customer_id));
			int accountId =account.getAccountId();
			boolean sucess1 = accountService.deleteAccount(accountId);
			boolean sucess2 = accountService.deleteAccountRole(accountId);
			model.addAttribute(IS_SUCCESS, sucess && sucess1 && sucess2);
			jsonUtil.sendJson(response, model);
		}

		@RequestMapping(value = "customer/mobile_viewCustomer.do", method = RequestMethod.GET)
		public void viewCustomer(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {
			String customerId = request.getParameter("customer_id");
			int id = Integer.parseInt(customerId);
			Customer c = this.customerService.findByCustomerId(id);

			model.addAttribute("customer", c);
			jsonUtil.sendJson(response, model);
		}
		
		public String test = "test";
		@Autowired
		private AccountService accountService;
		@Autowired
		private EmployeeService employeeService;
		@Autowired
		private CustomerService customerService;
		@Autowired
		private CommonService commonService;
		@Autowired
		private JSONUtil jsonUtil;
	}

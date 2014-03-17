package nju.software.util;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Account;
import nju.software.dataobject.Employee;
import nju.software.service.AccountService;
import nju.software.service.EmployeeService;

import org.drools.SystemEventListener;
import org.drools.SystemEventListenerFactory;
import org.jbpm.task.User;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.TaskServiceSession;
import org.jbpm.task.service.hornetq.HornetQTaskServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

/**
 * HorentQ服务器的启动
 * @author william
 */
@Controller("HumanTaskStartupServlet")
public class HumanTaskStartupServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("entityManagerFactory")
	private EntityManagerFactory emf;
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private EmployeeService employeeService;
//	@Autowired
//	private TeacherService teacherService;

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("************启动HorentQ服务器*************" + emf);
		try {
			System.out.println("task started");
			SystemEventListener listener = SystemEventListenerFactory
					.getSystemEventListener();
			TaskService taskService = new TaskService(emf, listener);
			System.out.println("task finished");
			// 添加必要的用户
			TaskServiceSession taskSession = taskService.createSession();
			taskSession.addUser(new User("Administrator"));
			taskSession.addUser(new User("WULIUZHUGUAN"));
			taskSession.addUser(new User("SHICHANGZHUGUAN"));
			taskSession.addUser(new User("SHICHANGZHUANYUAN"));
			taskSession.addUser(new User("CAIGOUZHUGUAN"));
			taskSession.addUser(new User("ZHIJIANZHUGUAN"));
			taskSession.addUser(new User("SHENGCHANZHUGUAN"));
			taskSession.addUser(new User("SHEJIZHUGUAN"));
			taskSession.addUser(new User("CAIWUZHUGUAN"));
//			taskSession.addUser(new User("jwy"));
//			taskSession.addUser(new User("wy"));
//			taskSession.addUser(new User("gjd"));
//			taskSession.addUser(new User("judge1"));
//			taskSession.addUser(new User("judge2"));
//			taskSession.addUser(new User("secretary"));
//			taskSession.addUser(new User("defenseteacher1"));
//			taskSession.addUser(new User("defenseteacher2"));
//			taskSession.addUser(new User("defenseteacher3"));
			System.out.println("user finished");

			// 添加教务员
//			if(teacherService.getTeacherByTeacherNumber("jwy") == null){
//				try{
//					Teacher teacher = new Teacher();
//					teacher.setTeacherName("教务员");
//					teacher.setTeacherNumber("jwy");
//					teacher.setTeacherClasses(Constants.INNERTEACHER);
//					teacher.setTeacherJobTitle(Constants.INSTRUCTOR);
//					teacher.setTeacherEducation(Constants.DOCTOR);
//					teacher.setTeacherEmail("jwy@software.nju.edu.cn");
//					teacher.setTeacherPhone("15292387443");
//					teacher.setTeacherQualification(Constants.ENGINEERING_MASTER_TUTOR);
//					teacherService.addTeacherInfo(teacher);
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//					
//				int teacherId = teacherService.getTeacherByTeacherNumber("jwy").getTeacherId();
//				Account account = new Account();
//				account.setAccountName("jwy");
//				account.setAccountPassword("jwy");
//				account.setAccountLevel(Constants.DEAN);
//				account.setTeacherId(teacherId);
//				accountService.updateAccount(account);
//			}
			
			// 添加管理员
			if (accountService.getAccountByUsername("admin") == null) {
				String employeeName = "管理员";
				Short sex = 1;
				Short age = 24;
				String department = "行政";
//				@SuppressWarnings("deprecation")
//				Timestamp entryTime = new  Timestamp(90, 3, 2, 8, 0, 0, 0);
				String time = "1990-04-02 08:00:00";
				Timestamp entryTime = Timestamp.valueOf(time);
				int directLeader = -1;
				String employeeLevel = "-1";
				String employeeState = "正常";
				String chinaId = "888888199004028888";
				Employee employee = new Employee(employeeName, sex, age, department, entryTime, directLeader, employeeLevel, employeeState, chinaId);
				employeeService.addEmployee(employee);
				int employeeId = employee.getEmployeeId();
				String userPassword = SecurityUtil.md5hex("admin");
				
				Account account = new Account(employeeId, "ADMIN", userPassword, "admin", "ADMIN", employeeName);
				accountService.addAccount(account);
			}

			// 开启HornetQTaskServer
			HornetQTaskServer server = new HornetQTaskServer(taskService, 5445);
			Thread thread = new Thread(server);
			thread.start();

		} catch (Throwable t) {
			throw new RuntimeException("不能开启HornetQ服务器", t);

		}

	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest reqquest,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendError(1001, "本页面不允许发送请求");
	}
}

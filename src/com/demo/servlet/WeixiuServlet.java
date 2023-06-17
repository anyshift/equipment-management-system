package com.demo.servlet;

import com.demo.service.ShebeiService;
import com.demo.service.UserService;
import com.demo.service.impl.ShebeiServiceImpl;
import com.demo.service.impl.UserServiceImpl;
import com.demo.util.Util;
import com.demo.service.WeixiuService;
import com.demo.service.impl.WeixiuServiceImpl;
import com.demo.vo.User;
import com.demo.vo.Weixiu;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 维修模块的Servlet控制层，负责接收页面传过来的请求参数，根据action参数的值来确定页面要执行的具体操作<br>
 * 而后再调用WeixiuService业务层的方法来处理具体的业务，最后将处理完成的结果返回或跳转至相应页面
 */
//@WebServlet("/WeixiuServlet")
public class WeixiuServlet extends HttpServlet {

    /**
     * 处理Post请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //过滤编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = Util.decode(request, "action");
        if ("add".equals(action)) {//增加
            Weixiu vo = new Weixiu();
            //取出页面传进来的各个数据，并设置到Weixiu对象的属性里
            vo.setWeixiuNo(Util.decode(request, "weixiuNo"));
            vo.setWeixiuName(Util.decode(request, "weixiuName"));
            vo.setWeixiuGuzhang(Util.decode(request, "weixiuGuzhang"));
            vo.setWeixiuFeiyong(Util.decode(request, "weixiuFeiyong"));
            vo.setWeixiuDate(Util.decode(request, "weixiuDate"));
            vo.setWeixiuRen(Util.decode(request, "weixiuRen"));
            vo.setWeixiuPhone(Util.decode(request, "weixiuPhone"));
            vo.setWeixiuStatus(Util.decode(request, "weixiuStatus"));
            vo.setWeixiuText(Util.decode(request, "weixiuText"));
            WeixiuService weixiuService = new WeixiuServiceImpl();
            //调用Service层增加方法（add），增加记录
            weixiuService.add(vo);
            this.redirectList(request, response);
        } else if ("delete".equals(action)) {//删除
            //取出表要删除的维修记录的主键
            long id = Long.parseLong(Util.decode(request, "id"));
            WeixiuService weixiuService = new WeixiuServiceImpl();
            //调用Service层删除方法（delete），将对应的记录删除
            weixiuService.delete(id);
            this.redirectList(request, response);
        } else if ("edit".equals(action)) {//修改
            //取出页面传进来的各个数据，并设置到Weixiu对象的属性里
            Weixiu vo = new Weixiu();
            vo.setId(Long.valueOf(Util.decode(request, "id")));
            vo.setWeixiuNo(Util.decode(request, "weixiuNo"));
            vo.setWeixiuName(Util.decode(request, "weixiuName"));
            vo.setWeixiuGuzhang(Util.decode(request, "weixiuGuzhang"));
            vo.setWeixiuFeiyong(Util.decode(request, "weixiuFeiyong"));
            vo.setWeixiuDate(Util.decode(request, "weixiuDate"));
            vo.setWeixiuRen(Util.decode(request, "weixiuRen"));
            vo.setWeixiuPhone(Util.decode(request, "weixiuPhone"));
            vo.setWeixiuStatus(Util.decode(request, "weixiuStatus"));
            vo.setWeixiuText(Util.decode(request, "weixiuText"));
            WeixiuService weixiuService = new WeixiuServiceImpl();
            //调用Service层更新方法（update），更新记录
            weixiuService.update(vo);
            this.redirectList(request, response);
        } else if ("acceptTask".equalsIgnoreCase(action)) {
            long deviceId = Long.parseLong(Util.decode(request, "deviceId"));
            WeixiuService weixiuService = new WeixiuServiceImpl();
            if (weixiuService.updateRepairStatus(deviceId, "正在维修", "等待维修完成", null)) {
                ShebeiService shebeiService = new ShebeiServiceImpl();
                shebeiService.updateShebeiStatus(deviceId, "正在维修", false);
                weixiuService.updateRepairDispatchStatus(deviceId, "正在维修");
            }
            this.redirectList(request, response);
        } else if ("taskFinished".equalsIgnoreCase(action)) {
            long deviceId = Long.parseLong(Util.decode(request, "deviceId"));
            Double price = Double.valueOf(Util.decode(request, "price"));
            WeixiuService weixiuService = new WeixiuServiceImpl();
            if (weixiuService.updateRepairStatus(deviceId, "维修完成", "设备恢复正常", price)) {
                ShebeiService shebeiService = new ShebeiServiceImpl();
                shebeiService.updateShebeiStatus(deviceId, "正常", false);
                shebeiService.deleteReportRecord(deviceId);
                weixiuService.deleteRepairDispatchRecord(deviceId);
            }
            this.redirectList(request, response);
        } else if ("transferTask".equalsIgnoreCase(action)) {
            long uid = Long.parseLong(Util.decode(request, "uid"));
            long deviceId = Long.parseLong(Util.decode(request, "deviceId"));
            WeixiuService weixiuService = new WeixiuServiceImpl();
            if (weixiuService.transferTask(deviceId, uid)) {
                weixiuService.updateRepairer(deviceId, uid);
            }
            this.redirectList(request, response);
        } else if ("deviceDestroyed".equalsIgnoreCase(action)) {
            long deviceId = Long.parseLong(Util.decode(request, "deviceId"));
            WeixiuService weixiuService = new WeixiuServiceImpl();
            weixiuService.deleteRepairDispatchRecord(deviceId);
            weixiuService.updateRepairStatus(deviceId, "损坏", "设备已报废", null);
            ShebeiService shebeiService = new ShebeiServiceImpl();
            shebeiService.deleteReportRecord(deviceId);
            shebeiService.updateShebeiStatus(deviceId, "损坏", Boolean.FALSE);
            this.redirectList(request, response);
        } else if ("get".equalsIgnoreCase(action) || "editPre".equalsIgnoreCase(action)) {//根据主键ID，查询详情信息并跳转到详情页面或编辑页面
            Serializable id = Util.decode(request, "id");//取出页面传入的主键，用于查询详情
            WeixiuService weixiuService = new WeixiuServiceImpl();
            Weixiu vo = weixiuService.get(id);
            request.getSession().setAttribute("vo", vo);
            String to = "get".equalsIgnoreCase(action) ? "info" : "edit";//判断是去详情显示页面还是编辑页面
            response.sendRedirect("weixiu_" + to + ".jsp");
        } else {//默认去列表页面
            this.redirectList(request, response);
        }
    }

    /**
     * 处理Get请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);//Get请求和Post请求的处理是一样的，所以把request、response转交给Post方法就好
    }

    /**
     * 根据参数，查询出条例条件的记录集合，最后将数据返回给调用处或者将数据集合设置到session域里，再跳转到对应的列表页面
     *
     * @param request
     * @param response
     */
    private void redirectList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //查询列和关键字
        String searchColumn = Util.decode(request, "searchColumn");
        String keyword = Util.decode(request, "keyword");
        Map<String, Object> params = new HashMap();//用来保存控制层传进来的参数(查询条件)
        params.put("keyword", keyword);//查询的关键字

        if (params.get("keyword") != null && !"".equals(params.get("keyword"))) {
            String keywordStr = (String)params.get("keyword");
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(keywordStr);
            if (isNum.matches()) {
                params.put("searchColumn", "weixiu_no");//要查询的列
            } else {
                params.put("searchColumn", searchColumn);//要查询的列
            }
        }

        User loginUser = (User)request.getSession().getAttribute("loginUser");
        Boolean listMyRepairWork = false;
        String repairer = "";
        if (!"管理员".equalsIgnoreCase(loginUser.getUserType())) {
            listMyRepairWork = true;
            repairer = loginUser.getRealName();
        }

        WeixiuService weixiuService = new WeixiuServiceImpl();
        Map<String, Object> map = weixiuService.list(params, listMyRepairWork, repairer);
        request.getSession().setAttribute("list", map.get("list"));

        Integer totalRecord = (Integer) map.get("totalCount");//根据查询条件取出对应的总记录数，用于分页
        String pageNum = Util.decode(request, "pageNum");//封装分页参数
        com.demo.util.PageBean<Object> pb = new com.demo.util.PageBean(Integer.valueOf(pageNum != null ? pageNum : "1"), totalRecord);
        params.put("startIndex", pb.getStartIndex());
        params.put("pageSize", pb.getPageSize());


        List list = (List) weixiuService.list(params, listMyRepairWork, repairer).get("list");//根据分页参数startIndex、pageSize查询出来的最终结果list
        pb.setServlet("WeixiuServlet");
        pb.setSearchColumn(searchColumn);
        pb.setKeyword(keyword);
        pb.setList(list);

        UserService userService = new UserServiceImpl();
        List<User> workers = userService.getWorkers();
        List<User> newWorkers = new ArrayList<>();
        workers.forEach(worker -> {
            if (!loginUser.getRealName().equalsIgnoreCase(worker.getRealName())) {
                newWorkers.add(worker);
            }
        });

        request.getSession().setAttribute("pageBean", pb);
        request.getSession().setAttribute("list", pb.getList());
        request.getSession().setAttribute("workers", newWorkers);

        response.sendRedirect("weixiu_list.jsp");
    }
}

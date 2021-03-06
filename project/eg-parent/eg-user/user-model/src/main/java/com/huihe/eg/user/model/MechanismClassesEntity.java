package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;

@ApiModel(value="机构班级",description="机构班级属性说明")
public class MechanismClassesEntity extends PageInfo {
    private static final long serialVersionUID = 3285680228955503628L;
    /**
     * id
     */
    @ApiModelProperty(value="id",example="1")
    private Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date create_time;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="开始时间")
    private Date search_start_time;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="结束时间")
    private Date search_end_time;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date update_time;

    /**
     * 机构id
     */
    @ApiModelProperty(value="机构id",example="1")
    private Long mechanism_id;

    /**
     * 房间名
     */
    @ApiModelProperty(value="房间名")
    private String name;

    /**
     * 老师名
     */
    @ApiModelProperty(value="老师名")
    private String master_name;

    /**
     * 课程名字
     */
    @ApiModelProperty(value="课程名字")
    private String course_name;

    /**
     * 1 不可用 2 可用 3 被合并
     */
    @ApiModelProperty(value="1 不可用 2 可用 3 被合并",example="1")
    private Integer status;


    /**
     * 已排总数
     */
    @ApiModelProperty(value="已排总数",example="1")
    private Integer scheduled_count;

    /**
     * 已上总数
     */
    @ApiModelProperty(value="已上总数",example="1")
    private Integer end_count;

    /**
     * 被合并的id
     */
    @ApiModelProperty(value="被合并的id",example="1")
    private Long merger_id;

    /**
     * 班级人数
     */
    @ApiModelProperty(value="班级人数",example="1")
    private Integer student_count;

    /**
     * 人数最大值
     */
    @ApiModelProperty(value="人数最大值",example="1")
    private Integer student_count_max;

    /**
     * 商品id
     */
    @ApiModelProperty(value="商品id",example="1")
    private Long master_set_price_id;

    /**
     * 老师id
     */
    @ApiModelProperty(value="老师id",example="1")
    private Long master_id;

    @ApiModelProperty(value = "星期参数")
    private String weekOfDays;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "日期")
    private Date date;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "日期")
    private Date start_date;

    @ApiModelProperty(value = "日期")
    private String start_time;

    @ApiModelProperty(value = "日期")
    private String end_time;

    @ApiModelProperty(value = "map杂项")
    private Map<String,Object> map;

    @ApiModelProperty(value = "类型 week 星期 calendar 日历")
    private String type;

    @ApiModelProperty(value = "类型 week 星期 calendar 日历")
    private Boolean is_circulation;

    @ApiModelProperty(value = "类型 week 星期 calendar 日历")
    private Boolean is_scheduling_over;

    @ApiModelProperty(value = "是否重复")
    private Boolean is_repeat;

    @ApiModelProperty(value = "是否排完")
    private Boolean is_all;

    @ApiModelProperty(value = "教室名")
    private String classroom_name;

    @ApiModelProperty(value = "被合并的课程id")
    private String merger_ids;

    public String getMaster_name() {
        return master_name;
    }

    public void setMaster_name(String master_name) {
        this.master_name = master_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Integer getScheduled_count() {
        return scheduled_count;
    }

    public void setScheduled_count(Integer scheduled_count) {
        this.scheduled_count = scheduled_count;
    }

    public Integer getEnd_count() {
        return end_count;
    }

    public void setEnd_count(Integer end_count) {
        this.end_count = end_count;
    }

    public String getMerger_ids() {
        return merger_ids;
    }

    public void setMerger_ids(String merger_ids) {
        this.merger_ids = merger_ids;
    }

    public Boolean getIs_all() {
        return is_all;
    }

    public void setIs_all(Boolean is_all) {
        this.is_all = is_all;
    }

    public Boolean getIs_repeat() {
        return is_repeat;
    }

    public void setIs_repeat(Boolean is_repeat) {
        this.is_repeat = is_repeat;
    }

    public String getClassroom_name() {
        return classroom_name;
    }

    public void setClassroom_name(String classroom_name) {
        this.classroom_name = classroom_name;
    }

    public Boolean getIs_scheduling_over() {
        return is_scheduling_over;
    }

    public void setIs_scheduling_over(Boolean is_scheduling_over) {
        this.is_scheduling_over = is_scheduling_over;
    }

    public Boolean getIs_circulation() {
        return is_circulation;
    }

    public void setIs_circulation(Boolean is_circulation) {
        this.is_circulation = is_circulation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getSearch_start_time() {
        return search_start_time;
    }

    public void setSearch_start_time(Date search_start_time) {
        this.search_start_time = search_start_time;
    }

    public Date getSearch_end_time() {
        return search_end_time;
    }

    public void setSearch_end_time(Date search_end_time) {
        this.search_end_time = search_end_time;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Long getMaster_id() {
        return master_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setMaster_id(Long master_id) {
        this.master_id = master_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getWeekOfDays() {
        return weekOfDays;
    }

    public void setWeekOfDays(String weekOfDays) {
        this.weekOfDays = weekOfDays;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * id
     * @return id id
     */
    public Long getId() {
        return id;
    }

    /**
     * id
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 创建时间
     * @param create_time 创建时间
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 更新时间
     * @return update_time 更新时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 更新时间
     * @param update_time 更新时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 机构id
     * @return mechanism_id 机构id
     */
    public Long getMechanism_id() {
        return mechanism_id;
    }

    /**
     * 机构id
     * @param mechanism_id 机构id
     */
    public void setMechanism_id(Long mechanism_id) {
        this.mechanism_id = mechanism_id;
    }

    /**
     * 房间名
     * @return name 房间名
     */
    public String getName() {
        return name;
    }

    /**
     * 房间名
     * @param name 房间名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 1 不可用 2 可用 3 被合并
     * @return status 1 不可用 2 可用 3 被合并
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1 不可用 2 可用 3 被合并
     * @param status 1 不可用 2 可用 3 被合并
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 被合并的id
     * @return merger_id 被合并的id
     */
    public Long getMerger_id() {
        return merger_id;
    }

    /**
     * 被合并的id
     * @param merger_id 被合并的id
     */
    public void setMerger_id(Long merger_id) {
        this.merger_id = merger_id;
    }

    /**
     * 班级人数
     * @return student_count 班级人数
     */
    public Integer getStudent_count() {
        return student_count;
    }

    /**
     * 班级人数
     * @param student_count 班级人数
     */
    public void setStudent_count(Integer student_count) {
        this.student_count = student_count;
    }

    /**
     * 人数最大值
     * @return student_count_max 人数最大值
     */
    public Integer getStudent_count_max() {
        return student_count_max;
    }

    /**
     * 人数最大值
     * @param student_count_max 人数最大值
     */
    public void setStudent_count_max(Integer student_count_max) {
        this.student_count_max = student_count_max;
    }

    /**
     * 商品id
     * @return master_set_price_id 商品id
     */
    public Long getMaster_set_price_id() {
        return master_set_price_id;
    }

    /**
     * 商品id
     * @param master_set_price_id 商品id
     */
    public void setMaster_set_price_id(Long master_set_price_id) {
        this.master_set_price_id = master_set_price_id;
    }
}
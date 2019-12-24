package com.github.adminfaces.starter.bean;

import static java.util.Calendar.JANUARY;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Named
@ViewScoped
public class ScheduleJava8View {

	private ScheduleModel eventModel;

	private ScheduleEvent event = new DefaultScheduleEvent();

	private boolean showWeekends = true;
	private boolean tooltip = true;
	private boolean allDaySlot = true;
	private String locale="en";
	private String timeZone="IST";
	private String clientTimeZone="IST";
	private String columnHeaderFormat="";

	@PostConstruct
	public void init() {
		eventModel = new DefaultScheduleModel();
		eventModel.addEvent(buildEvent("New year", getDate(2020, JANUARY, 1),null, "First day of new year", true));
		eventModel.addEvent(buildEvent("JSS reunion", getDate(2020, JANUARY, 11, 11, 0), getDate(2020, JANUARY, 11, 15, 0), "Reunion ", false));
		eventModel.addEvent(buildEvent("Republic day", getDate(2020, JANUARY, 26), null,"70th republic day", true));
	}

	private DefaultScheduleEvent buildEvent(String title, Date startDate, Date endDate, String description, boolean allDayEvent) {
		DefaultScheduleEvent defaultScheduleEvent = new DefaultScheduleEvent();
		defaultScheduleEvent.setTitle(title);
		defaultScheduleEvent.setStartDate(startDate);
		if (endDate == null && allDayEvent) {
			Calendar endDateCalendar = Calendar.getInstance();
			endDateCalendar.setTime(startDate);
			endDateCalendar.setTimeZone(TimeZone.getTimeZone("IST"));
			defaultScheduleEvent.setEndDate(endDateCalendar.getTime());
		} else {
			defaultScheduleEvent.setEndDate(endDate);
		}
		defaultScheduleEvent.setAllDay(allDayEvent);
		defaultScheduleEvent.setDescription(description);
		return defaultScheduleEvent;
	}

	private Date getDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
		calendar.set(year, month, date);
		return calendar.getTime();
	}

	private Date getDate(int year, int month, int date, int hour, int minute) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
		calendar.set(year, month, date, hour, minute, 1);
		return calendar.getTime();
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public void addEvent() {
		if (event.isAllDay()) {
			//see https://github.com/primefaces/primefaces/issues/1164
		}

		if(event.getId() == null)
			eventModel.addEvent(event);
		else
			eventModel.updateEvent(event);

		event = new DefaultScheduleEvent();
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Delta:" + event);

		addMessage(message);
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Start-Delta:" + event + ", End-Delta: " + event);

		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public boolean isShowWeekends() {
		return showWeekends;
	}

	public void setShowWeekends(boolean showWeekends) {
		this.showWeekends = showWeekends;
	}

	public boolean isTooltip() {
		return tooltip;
	}

	public void setTooltip(boolean tooltip) {
		this.tooltip = tooltip;
	}

	public boolean isAllDaySlot() {
		return allDaySlot;
	}

	public void setAllDaySlot(boolean allDaySlot) {
		this.allDaySlot = allDaySlot;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getClientTimeZone() {
		return clientTimeZone;
	}

	public void setClientTimeZone(String clientTimeZone) {
		this.clientTimeZone = clientTimeZone;
	}

	public String getColumnHeaderFormat() {
		return columnHeaderFormat;
	}

	public void setColumnHeaderFormat(String columnHeaderFormat) {
		this.columnHeaderFormat = columnHeaderFormat;
	}
}

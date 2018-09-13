package com.sanjaygoyaljpr.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sanjaygoyaljpr.constant.ApplicationConstants.BOOKING_STATUS;
import com.sanjaygoyaljpr.misc.MessageInfo;
import com.sanjaygoyaljpr.persistence.model.ConferenceRoom;
import com.sanjaygoyaljpr.persistence.model.User;
import com.sanjaygoyaljpr.persistence.model.UserConferenceRoom;
import com.sanjaygoyaljpr.service.IConferenceRoomService;
import com.sanjaygoyaljpr.service.IUserConferenceRoomService;
import com.sanjaygoyaljpr.service.IUserService;
import com.sanjaygoyaljpr.wrapper.FullCalenderEvent;

@Controller
public class BookingController {

	@Autowired
	IUserService userService;

	@Autowired
	IConferenceRoomService conferenceRoomService;

	@Autowired
	IUserConferenceRoomService userConferenceRoomService;

	@RequestMapping("/home")
	public ModelAndView home() {
		String redirect = "";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		if (user != null) {
			redirect = "my-wall";
		} else {
			redirect = "login";
		}
		ModelAndView mav = new ModelAndView(redirect);
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping("/bookConference")
	public ModelAndView signIn(Model model) {
		String redirect = "";
		ModelAndView mav = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		if (user != null) {
			redirect = "book-conference";
			mav = new ModelAndView(redirect);
			mav.addObject("conferenceRooms", conferenceRoomService.findAll());
			mav.addObject("user", user);
		} else {
			redirect = "login";
			mav = new ModelAndView(redirect);
		}

		return mav;
	}

	@RequestMapping("/addConference")
	public ModelAndView addConference(Model model) {
		String redirect = "";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		if (user != null) {
			redirect = "add-conference-room";
		} else {
			redirect = "login";
		}
		ModelAndView mav = new ModelAndView(redirect);
		mav.addObject("conferenceRoom", new ConferenceRoom());
		mav.addObject("user", user);
		return mav;
	}

	@PostMapping("/createConference")
	public ModelAndView createConference(@Valid ConferenceRoom conferenceRoom, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();

		if (user == null) {
			modelAndView.setViewName("login");
			return modelAndView;
		}

		// Check duplicate conference room name
		if (!StringUtils.isEmpty(conferenceRoom.getConfRoomName())) {
			ConferenceRoom conferenceRoomExists = conferenceRoomService.findByConfRoomNameContainingIgnoreCase(conferenceRoom.getConfRoomName());
			if (conferenceRoomExists != null) {
				bindingResult.rejectValue("confRoomName", "error.user", "*There is already a conference room registered with the this name");
				modelAndView.setViewName("add-conference-room");
			}
		}

		if (conferenceRoom.getSeatCapacity() == null) {
			bindingResult.rejectValue("seatCapacity", "error.user", "*Please provide seat capacity");
		} else if (conferenceRoom.getSeatCapacity().intValue() < 1 || conferenceRoom.getSeatCapacity().intValue() > 99) {
			bindingResult.rejectValue("seatCapacity", "error.user", "*Seat capacity should be between 1 and 99.");
		}

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("add-conference-room");
		} else {
			conferenceRoom.setCreatedDate(Calendar.getInstance());
			conferenceRoom.setCreatedBy(user.getId());
			conferenceRoomService.save(conferenceRoom);
			modelAndView.addObject("successMessage", "Conference Room has been added successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("add-conference-room");
		}

		modelAndView.addObject("user", user);
		return modelAndView;
	}

	@RequestMapping("/myBookings")
	public ModelAndView myBookings(Model model) {
		String redirect = "";
		ModelAndView mav = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		if (user != null) {
			redirect = "my-booking";
			mav = new ModelAndView(redirect);
			List<UserConferenceRoom> userConferenceRooms = userConferenceRoomService.findByUserId(user.getId());
			int i = 1;
			for (UserConferenceRoom userConferenceRoom : userConferenceRooms) {
				userConferenceRoom.setSrNo(i);

				// For Cancelled user
				if (userConferenceRoom.getCancelledBy() != null) {
					if (userConferenceRoom.getUserId() != userConferenceRoom.getCancelledBy()) {
						User users = userService.findOne(userConferenceRoom.getCancelledBy());
						if (users != null) {
							userConferenceRoom.setCancelledByName(users.getFirstName() + " " + users.getLastName());
						}
					} else {
						userConferenceRoom.setCancelledByName(user.getFirstName() + " " + user.getLastName());
					}
				}

				ConferenceRoom conferenceRoom = conferenceRoomService.findOne(userConferenceRoom.getConferenceRoomId());
				if (conferenceRoom != null) {
					userConferenceRoom.setConfRoomName(conferenceRoom.getConfRoomName());
				}
				userConferenceRoom.setStartDateOfBooking(userConferenceRoom.getStartDateOfBooking().replace("T", " "));
				userConferenceRoom.setEndDateOfBooking(userConferenceRoom.getEndDateOfBooking().replace("T", " "));
				i++;
			}

			mav.addObject("userConferenceRooms", userConferenceRooms);
			mav.addObject("user", user);
		} else {
			redirect = "login";
			mav = new ModelAndView(redirect);
		}

		return mav;
	}

	@RequestMapping("/getAllBookings")
	public ModelAndView getAllBookings(Model model) {
		String redirect = "";
		ModelAndView mav = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		if (user != null) {
			redirect = "all-booking";
			mav = new ModelAndView(redirect);
			List<UserConferenceRoom> userConferenceRooms = userConferenceRoomService.findAll();
			int i = 1;
			for (UserConferenceRoom userConferenceRoom : userConferenceRooms) {
				userConferenceRoom.setSrNo(i);
				User users = userService.findOne(userConferenceRoom.getUserId());
				if (users != null) {
					userConferenceRoom.setUserName(users.getFirstName() + " " + users.getLastName());
				}

				// For Cancelled user
				if (userConferenceRoom.getCancelledBy() != null) {
					if (userConferenceRoom.getUserId() != userConferenceRoom.getCancelledBy()) {
						users = userService.findOne(userConferenceRoom.getCancelledBy());
						if (users != null) {
							userConferenceRoom.setCancelledByName(users.getFirstName() + " " + users.getLastName());
						}
					} else {
						userConferenceRoom.setCancelledByName(users.getFirstName() + " " + users.getLastName());
					}
				}

				ConferenceRoom conferenceRoom = conferenceRoomService.findOne(userConferenceRoom.getConferenceRoomId());
				if (conferenceRoom != null) {
					userConferenceRoom.setConfRoomName(conferenceRoom.getConfRoomName());
				}

				userConferenceRoom.setStartDateOfBooking(userConferenceRoom.getStartDateOfBooking().replace("T", " "));
				userConferenceRoom.setEndDateOfBooking(userConferenceRoom.getEndDateOfBooking().replace("T", " "));

				i++;
			}
			mav.addObject("userConferenceRooms", userConferenceRooms);
			mav.addObject("user", user);
		} else {
			redirect = "login";
			mav = new ModelAndView(redirect);
		}

		return mav;
	}

	@RequestMapping("/conferenceRoomList")
	public ModelAndView getConferenceRooms(Model model) {
		ModelAndView modelAndView = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		if (user != null) {
			modelAndView = new ModelAndView("conference-room-list");
			List<ConferenceRoom> conferenceRooms = conferenceRoomService.findAll();
			for (ConferenceRoom conferenceRoo : conferenceRooms) {
				User users = userService.findOne(conferenceRoo.getCreatedBy());
				conferenceRoo.setUserName(users.getFirstName() + " " + users.getLastName());
			}
			modelAndView.addObject("conferenceRooms", conferenceRooms);
			modelAndView.addObject("user", user);
		} else {
			modelAndView = new ModelAndView("login");
		}

		return modelAndView;
	}

	/**
	 * This will load all events in Calendar for all bookings.
	 */
	@RequestMapping("/loadEvents")
	public ResponseEntity<?> loadCalenderEvents(@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "end", required = false) String end) {
		List<FullCalenderEvent> events = new ArrayList<>();

		List<UserConferenceRoom> userConferenceRooms = userConferenceRoomService.findAll();
		for (UserConferenceRoom ucr : userConferenceRooms) {
			User users = userService.findOne(ucr.getUserId());
			if (users != null) {
				ucr.setUserName(users.getFirstName() + " " + users.getLastName());
			}
			ConferenceRoom conferenceRoom = conferenceRoomService.findOne(ucr.getConferenceRoomId());
			if (conferenceRoom != null) {
				ucr.setConfRoomName(conferenceRoom.getConfRoomName());
			}

			FullCalenderEvent event = new FullCalenderEvent(ucr.getId(), ucr.getTitle(), ucr.getStartDateOfBooking(), ucr.getEndDateOfBooking(),
					ucr.getUserName(), ucr.getConfRoomName());
			events.add(event);
		}

		return ResponseEntity.ok(events);
	}

	@RequestMapping("/bookConferenceRoom")
	public ModelAndView bookingConference(@RequestParam("title") String title, @RequestParam("confRoomId") Long confRoomId,
			@RequestParam("startDateOfBooking") String startDateOfBooking, @RequestParam("endDateOfBooking") String endDateOfBooking, Model model)
			throws ParseException {
		String redirect = "";
		ModelAndView mav = null;
		MessageInfo message = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		if (user != null) {
			redirect = "book-conference";
			message = new MessageInfo();

			// Check for existing booking
			if (false) {
				message.setMsg("Please choose different slot as this slot is already booked !!");
				message.setMsgType("ERROR");
			} else {
				UserConferenceRoom userConferenceRoom = new UserConferenceRoom();
				userConferenceRoom.setUserId(user.getId());
				userConferenceRoom.setConferenceRoomId(confRoomId);
				userConferenceRoom.setTitle(title);

				userConferenceRoom.setStartDateOfBooking(startDateOfBooking);
				userConferenceRoom.setEndDateOfBooking(endDateOfBooking);

				userConferenceRoom.setCreatedDate(Calendar.getInstance());
				userConferenceRoom.setStatus(BOOKING_STATUS.BOOKED.name());
				userConferenceRoomService.save(userConferenceRoom);

				message.setMsg("Conference Room Booking is Confirmed !!!");
				message.setMsgType("SUCCESS");
			}

			mav = new ModelAndView(redirect);
			mav.addObject("user", user);
			mav.addObject("conferenceRooms", conferenceRoomService.findAll());
			mav.addObject("message", message);
		} else {
			redirect = "login";
		}

		return mav;
	}

	@RequestMapping("/cancelUserBooking")
	public ResponseEntity<?> cancelConferenceRoom(@RequestParam("ucrId") Long ucrId) throws ParseException {
		MessageInfo message = new MessageInfo();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		if (user != null) {
			UserConferenceRoom userConferenceRoom = userConferenceRoomService.findOne(ucrId);
			userConferenceRoom.setStatus(BOOKING_STATUS.CANCELLED.name());
			userConferenceRoom.setCancelledBy(user.getId());
			userConferenceRoom.setCancelledDate(Calendar.getInstance());
			userConferenceRoomService.save(userConferenceRoom);
			message.setMsg("Conference Room Booking is Cancelled !!!");
			message.setMsgType("SUCCESS");
		}

		return ResponseEntity.ok(message);
	}

}

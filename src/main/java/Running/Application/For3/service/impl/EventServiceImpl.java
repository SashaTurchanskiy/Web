package Running.Application.For3.service.impl;

import Running.Application.For3.Exception.ClubNotFoundException;
import Running.Application.For3.Exception.EventNotFoundException;
import Running.Application.For3.dto.EventDto;
import Running.Application.For3.models.Club;
import Running.Application.For3.models.Event;
import Running.Application.For3.repo.ClubRepository;
import Running.Application.For3.repo.EventRepository;
import Running.Application.For3.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static Running.Application.For3.mapper.EventMapper.mapToEvent;
import static Running.Application.For3.mapper.EventMapper.mapToEventDto;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private ClubRepository clubRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ClubRepository clubRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public void createEvent(Long clubId, EventDto eventDto) {
        Optional<Club> optionalClub = clubRepository.findById(clubId);
        if (optionalClub.isPresent()) {
            Club club = optionalClub.get();
            Event event = mapToEvent(eventDto);
            event.setClub(club);
            eventRepository.save(event);
        } else {
            throw new ClubNotFoundException();
        }
    }

    @Override
    public List<EventDto> findAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> mapToEventDto(event)).collect(Collectors.toList());
    }

    @Override
    public EventDto findByEventId(Long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()){
            Event event = optionalEvent.get();
            return mapToEventDto(event);
        }else{
            throw new EventNotFoundException();
        }

    }

    @Override
    public void updateEvent(EventDto eventDto) {
        Event event = mapToEvent(eventDto);
        eventRepository.save(event);
    }

    @Override
    public void delete(Long eventId) {
        eventRepository.deleteById(eventId);
    }
}

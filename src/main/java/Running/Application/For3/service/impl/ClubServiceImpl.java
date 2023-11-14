package Running.Application.For3.service.impl;

import Running.Application.For3.Exception.ClubNotFoundException;
import Running.Application.For3.dto.ClubDto;
import Running.Application.For3.models.Club;
import Running.Application.For3.models.UserEntity;
import Running.Application.For3.repo.ClubRepository;
import Running.Application.For3.repo.UserRepository;
import Running.Application.For3.security.SecurityUtil;
import Running.Application.For3.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static Running.Application.For3.mapper.ClubMapper.mapToClub;
import static Running.Application.For3.mapper.ClubMapper.mapToClubDto;

@Service
public class ClubServiceImpl implements ClubService {


    private ClubRepository clubRepository;
    private UserRepository userRepository;
    @Autowired
    public ClubServiceImpl(ClubRepository clubRepository, UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ClubDto> findAllClubs() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map(club -> mapToClubDto(club)).collect(Collectors.toList());
    }

    @Override
    public Club saveClub(ClubDto clubDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Club club = mapToClub(clubDto);
        club.setCreatedBy(user);
        return clubRepository.save(club);
    }

    @Override
    public ClubDto findClubById(long clubId) {
        Optional<Club> optionalClub = clubRepository.findById(clubId);
        if (optionalClub.isPresent()) {
            Club club = optionalClub.get();
            return mapToClubDto(club);
        } else {
            throw new ClubNotFoundException();
        }
    }

    @Override
    public void updateClub(ClubDto clubDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Club club = mapToClub(clubDto);
        club.setCreatedBy(user);
        clubRepository.save(club);
    }

    @Override
    public void delete(long clubId) {
        clubRepository.deleteById(clubId);
    }

    @Override
    public List<ClubDto> searchClubs(String query) {
        List<Club> clubs = clubRepository.searchClubs(query);
        if (clubs.isEmpty()){
            throw new ClubNotFoundException();
        }
        return clubs.stream().map(club -> mapToClubDto(club)).collect(Collectors.toList());
    }
}


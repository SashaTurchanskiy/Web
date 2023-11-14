package Running.Application.For3.service;

import Running.Application.For3.dto.ClubDto;
import Running.Application.For3.models.Club;

import java.util.List;

public interface ClubService {
    List<ClubDto> findAllClubs();

    Club saveClub(ClubDto clubDto);

    ClubDto findClubById(long clubId);

    void updateClub(ClubDto club);

    void delete(long clubId);

    List<ClubDto> searchClubs(String query);
}

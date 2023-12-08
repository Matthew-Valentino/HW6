package mapper;

import model.Participant;
import java.util.List;

public interface ParticipantMapper {
    void insertParticipant(Participant participant);
    List<Participant> listParticipants();
}

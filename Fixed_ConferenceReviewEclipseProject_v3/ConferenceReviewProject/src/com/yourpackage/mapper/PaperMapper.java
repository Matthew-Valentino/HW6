package mapper;

import model.Paper;
import java.util.List;

public interface PaperMapper {
    List<Paper> listPapersWithHighRecommendation();
}

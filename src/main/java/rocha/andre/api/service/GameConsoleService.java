package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.gameConsoles.DTO.GameConsoleGameIdDTO;
import rocha.andre.api.domain.gameConsoles.DTO.GameConsoleReturnDTO;

public interface GameConsoleService {
    Page<GameConsoleReturnDTO> getAllGameConsolesByGameId(String gameId, Pageable pageable);
}

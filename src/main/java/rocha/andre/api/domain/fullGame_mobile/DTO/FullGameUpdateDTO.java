package rocha.andre.api.domain.fullGame_mobile.DTO;

import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.studios.DTO.StudioReturnFullGameInfo;

import java.util.ArrayList;

public record FullGameUpdateDTO(String name, Integer metacritic, Integer yearOfRelease, ArrayList<ConsoleReturnDTO> consoles, ArrayList<String> genres, ArrayList<StudioReturnFullGameInfo> studios) {
    public FullGameUpdateDTO(Game game, ArrayList<ConsoleReturnDTO> consoles, ArrayList<String> genres, ArrayList<StudioReturnFullGameInfo> studios) {
        this(game.getName(), game.getMetacritic(), game.getYearOfRelease(), consoles, genres, studios);
    }
}
package sk.tuke.kpi.kp.battleship.Core.User;

public enum StateGame {
    Initialized,
    Process,
    FirstPlayerShoots,
    FirstPlayerPlacesShips,
    SecondPlayerShoots,
    SecondPlayerPlacesShips,
    FirstPlayerWon,
    SecondPlayerWon,
    End
}

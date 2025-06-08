# battleship-game

Problem Statement
Design and implement a battleship game to be played between two players until one comes out
as the winner.
Requirements:

● The game will be played in a square area of the sea with NxN grids which will be called
a battlefield. “N” should be taken as input in your code.

● The battlefield will be divided in half between both the players. So in a NxN battlefield,
NxN/2 grids will belong to PlayerA and the other NxN/2 grids will belong to playerB

● The size and location of each ship will be taken as input. Each ship will be assumed to
be of Square shape. Both the players should be assigned equal fleet.

● The location of each ship in the NxN grids has to be taken as input (X, Y). X and Y
should be integers. For eg. if a ship “SH1” is at (2, 2) and has the size of 4, its corners
will be at (0, 0), (0, 4), (4, 0) and (4,4)

● Ships will remain stationary. No two ships should overlap with each other. However they
can touch boundaries with each other.

● Each player will fire one missile towards the enemy field during his turn using the
“random coordinate fire” strategy, which means the missile will hit at a random
coordinate of the opponent’s field. It might hit or miss the opponent ship. In either case
the turn is then transferred to the other player.

○ In case of a hit, the opponent’s ship is destroyed.

○ In case of a miss, nothing happens.

● No two missiles should ever be fired at the same coordinates throughout the course
of the game.

● When all the ships of a particular player has been destroyed, he loses the game.
The following APIs have to be implemented:
Mandatory:

● initGame(N)
This will initialize the game with a battlefield of size NxN. Where the left half of
N/2xN will be assigned to PlayerA and the right half will be assigned to PlayerB

● addShip(id, size, x position PlayerA, y position PlayerA, x position PlayerB, y position
PlayerB)
This will add a ship of given size at the given coordinates in both the player’s
fleet.

● startGame()
This will begin the game, where PlayerA will always take the first turn. The output
of each step should be printed clearly in the console.
For eg.
PlayerA’s turn: Missile fired at (2, 4).
“Hit”
. PlayerB’s ship with id “SH1”
destroyed.
PlayerB’s turn: Missile fired at (6, 1).
“Miss”
Optional

● viewBattleField()
This will display the battlefield as a NxN grid and all the ships along with the grids
occupied by each ship. PlayerA’s ship with id SH1 will be marked as A-SH1, with
id SH2 as A-SH2 and so on. Whereas PlayerB’s ships will be marked as B-SH1,
B-SH2 and so on.
Note: It should mark all the grids occupied by a ship and not just the center
coordinate.


flowchart TD
    %% External
    Console["Console / User"]:::external
    subgraph "Application"
        BA["BattleshipApplication\n(src/main/java/game/battleship/BattleshipApplication.java)"]:::external
    end

    %% Configuration Layer
    subgraph "Configuration" 
        GT["GameTheme"]:::config
    end

    %% Service Layer
    subgraph "Service" 
        GS["GameService"]:::service
    end

    %% Core Engine
    subgraph "Core Engine"
        GSM["GameSessionManager"]:::core
        GSMM["GameStateManager"]:::core
        TM["TurnManager"]:::core
        PM["PlayerStatsManager"]:::core
        FSM["GameSession"]:::core
        GDM["GameDisplayManager"]:::core
        FM["FiringManager"]:::core
        FLM["FleetManager"]:::core
    end

    %% Strategy & Factory
    subgraph "Strategy Layer"
        FS["FiringStrategy"]:::strategy
        HFS["HumanFiringStrategy"]:::strategy
        RFS["RandomFiringStrategy"]:::strategy
        SS["ScoreStrategy"]:::strategy
        DSS["DefaultScoreStrategy"]:::strategy
        VS["GameVisibilityStrategy"]:::strategy
        FVS["FullVisibilityStrategy"]:::strategy
        HVS["HiddenBotsVisibilityStrategy"]:::strategy
        RVS["RestrictedVisibilityStrategy"]:::strategy
    end

    subgraph "Factory Layer"
        FSF["FiringStrategyFactory"]:::factory
        GSF["GameSessionFactory"]:::factory
        VSF["GameVisibilityStrategyFactory"]:::factory
        PF["PlayerFactory"]:::factory
    end

    %% Repository
    subgraph "Repository"
        GSR["GameSessionRepository"]:::repo
        IMR["InMemoryGameSessionRepository"]:::repo
    end

    %% UI & Decorator
    subgraph "UI Layer"
        BR["BattlefieldRenderer"]:::ui
        TR["TableRenderer"]:::ui
        GTR["GameTextRenderer"]:::ui
        TC["TextColorizer"]:::ui
        CSR["CellSymbolRenderer"]:::ui
        GR["GridRenderer"]:::ui
        PLR["PlayerLabelRenderer"]:::ui
    end

    subgraph "Decorator Layer"
        TSD["TextStyleDecorator"]:::decorator
        FT["FormattedText"]:::decorator
        PT["PlainText"]:::decorator
    end

    %% Model & Validation & Util
    subgraph "Model Layer"
        P["Player"]:::model
        S["Ship"]:::model
        C["Cell"]:::model
        CO["Coordinate"]:::model
        Z["Zone"]:::model
        FR["FiringResult"]:::model
        CRI["CellRenderInfo"]:::model
        PC["PlayerConfig"]:::model
        PSS["PlayerSessionStats"]:::model
        FIVR["FleetInputValidationRequest"]:::model
        FIV["FiringInputValidationRequest"]:::model
        VR["ValidationResult"]:::model
    end

    subgraph "Validation Layer"
        V["Validator"]:::validation
        FV["FiringValidator"]:::validation
        FVa["FleetValidator"]:::validation
    end

    subgraph "Utility Helpers"
        CU["CoordinateUtils"]:::util
        UU["UIUtils"]:::util
        SAP["ShipAutoPlacer"]:::util
    end

    %% Relationships
    Console -->|"starts"| BA
    BA -->|"initGame()"| GS
    GS -->|"uses"| GSF
    GS -->|"uses"| FSF
    GS -->|"uses"| VSF
    GS -->|"creates session via"| GSF
    GSF --> FSM
    FSM --> IMR
    GS --> GSM
    GSM --> FLM
    FLM --> FSM
    GSM --> FM
    FM --> GSMM
    FM --> FSM
    GSMM --> PM
    PM --> DSS
    DSS --> PM
    FM -->|calls| FS
    FS --> HFS
    FS --> RFS
    FM --> VS
    VS --> FVS
    VS --> HVS
    VS --> RVS
    GS --> TR
    TR --> GTR
    GTR --> TC
    TC --> TSD
    TSD --> FT
    TSD --> PT
    TR --> BR
    BR --> CSR
    BR --> GR
    BR --> PLR
    IMR --> GSR
    GSR --> GSMM

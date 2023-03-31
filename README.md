# Othello Agent

### Problem Description
In this assignment, you will write a game-playing agent capable of playing 
Othello. Construct a general alpha-beta game-playing agent which takes the 
current state as input and returns a move/action to be made by the agent.


### Requirements
JDK 1.8 - is the baseline JDK against which this project is developed.


### Play Othello game with a Legendary-Computer-Agent

1. Open the project repository
2. Go to <b>\src</b> repository
3. Open and run the **OthelloGameTest.java** file
4. You'll be shown a list of valid moves for your convenience.
5. Choose your best move and wait for the agent's move.
6. Happy playing!



### Probe the Othello-Agent
I've implemented two evaluation functions for Othello Agent. First one is implemented in
<i>getNaiveEvaluation</i> method and Second one is implemented in
<i>getPriorityEvaluation</i> method, both of them are residing in 
<b>OthelloAgent.java</b> file.

>**Input:** Agent-Type, Depth<br>
>**Output:** Winner, Game Points

1. Open the project repository
2. Go to <b>\src</b> repository
3. Open the **OthelloDepthEvaluationValueTest.java** file
4. Set evaluation function for agent one to final static **AGENT_ONE_EVALUATION_FUNCTION** variable
5. Set depth for agent one to final static **AGENT_ONE_DEPTH** variable
6. Set evaluation function for agent two to final static **AGENT_TWO_EVALUATION_FUNCTION** variable
7. Set depth for agent two to final static **AGENT_TWO_DEPTH** variable
8. Run the file and compare the outputs
9. Happy testing!





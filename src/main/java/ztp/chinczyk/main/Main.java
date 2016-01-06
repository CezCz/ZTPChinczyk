package ztp.chinczyk.main;

import java.util.Scanner;

import ztp.chinczyk.model.GameLogic;
import ztp.chinczyk.model.GameState;
import ztp.chinczyk.model.ModelFacade;
import ztp.chinczyk.presenter.MainPresenter;
import ztp.chinczyk.presenter.interfaces.Presenter;

public class Main {
	public static void main(String[] args) {

//		Presenter p = new MainPresenter(new ModelFacade());
//		p.run(null);

		GameLogic g = new GameLogic();
		g.getGs().addPlayer("playerone");
		g.getGs().addPlayer("playertwo");
		g.prePlayerMove();
		GameState gs = g.getGs();
		int move;
		Scanner sc = new Scanner(System.in);
		while (!gs.getFinished()) {

			if (gs.getAnyMovable()) {
				System.out.println("dice roll: " + gs.getDiceRoll());
				System.out.println(gs.getAnyMovable());
				System.out
						.println("Gracz " + gs.getCurrentPlayer() + " twoj ruch pionki: " + gs.getCurrentPlayerPawns());
				int toMove;
				toMove = sc.nextInt();
				g.doMove(toMove);
			} else {
				System.out.println("Nic do powiedzenia");
				System.out.println(gs.getDiceRoll());
				g.doPass();
			}

		}

		System.out.println("Winner: " + gs.getWinner());

	}

}

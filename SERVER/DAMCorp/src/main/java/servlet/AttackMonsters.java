package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import game.Game;
import game.entity.Player;

/**
 * Servlet implementation class UpdateMonsters
 */
public class AttackMonsters extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Game game = new Game();

	public AttackMonsters() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Player player = game.retrievePlayer(request.getParameter("serverUniqueID"), request.getParameter("numLobby"));
		if (player != null) {

			String attacked = request.getParameter("attacked");

			int nLobby = Integer.parseInt(player.getNumLobby());

			System.out.println(attacked.length() + " AttackMonsters +++++++++++++++++++++++++++ " + attacked);

			game.setAttackedMonsters(nLobby, attacked);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

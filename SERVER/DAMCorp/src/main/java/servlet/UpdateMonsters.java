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
public class UpdateMonsters extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Game game = new Game();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateMonsters() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Player player = game.retrievePlayer(request.getParameter("serverUniqueID"), request.getParameter("numLobby"));
		if (player != null) {

			String mobs = request.getParameter("monsters");
			int nLobby = Integer.parseInt(player.getNumLobby());

			// System.out.println(mobs.length() + " ### UPDATE MONSTERS === " + mobs);

			game.setMonsters(nLobby, mobs);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

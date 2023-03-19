package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import game.Game;
import game.entity.Player;

/**
 * Servlet implementation class AddPlayers
 */
public class AddPlayers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Game game = new Game();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddPlayers() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());

		if (request.getParameter("listPlayers") != null) {
			String[] stringListPlayers = request.getParameter("listPlayers").split(";");
			Player[] players = new Player[stringListPlayers.length];

			for (int i = 0; i < players.length; i++) {
				players[i] = new Player(stringListPlayers[i], i + "");
				if (i == 0) {
					response.getWriter().append("" + i);
				} else {
					response.getWriter().append("," + i);
				}

			}

			if (players.length > 0) {
				String lobbyId = request.getParameter("lobbyId");
				if (lobbyId != null && !lobbyId.isEmpty() && !lobbyId.equalsIgnoreCase("null")) {
					game.addPlayers(players, Integer.parseInt(lobbyId));
				} else {
					game.addPlayers(players);
				}
				response.getWriter().append(";" + players[0].getNumLobby());
			}

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

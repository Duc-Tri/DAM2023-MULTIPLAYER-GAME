package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import game.Game;
import game.entity.Player;

/**
 * Servlet implementation class RetrieveMonsters
 */
public class RetrieveMonsters extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Game game = new Game();

	public RetrieveMonsters() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Player player = game.retrievePlayer(request.getParameter("serverUniqueID"), request.getParameter("numLobby"));

		if (player != null) {

			String mobs = game.retrieveMonstersStr(player);

			//System.out.println(mobs.length() + " RetrieveMonsters " + player.getUniqueID() + " ////////// " + mobs);

			response.getWriter().append(mobs);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

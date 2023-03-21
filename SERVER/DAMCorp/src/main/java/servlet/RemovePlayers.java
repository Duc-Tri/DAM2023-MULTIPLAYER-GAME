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
public class RemovePlayers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Game game = new Game();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RemovePlayers() {
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
			String lobbyId = request.getParameter("lobbyId");
			if (lobbyId != null && !lobbyId.isEmpty() && !lobbyId.equalsIgnoreCase("null")) {
				String[] stringListPlayers = request.getParameter("listPlayers").split(";");	
				for(int i = 0  ; i < stringListPlayers.length; i++) {
					game.removePlayer(lobbyId,stringListPlayers[i]);			
				}
		
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

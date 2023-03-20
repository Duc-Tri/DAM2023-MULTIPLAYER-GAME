package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import game.Game;
import game.entity.Player;

/**
 * Servlet implementation class RetrieveMate
 */
public class RetrieveMate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Game game = new Game();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RetrieveMate() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String serverUniqueID = request.getParameter("serverUniqueID");
		String numLobby = request.getParameter("numLobby");
		if (serverUniqueID != null && !serverUniqueID.isEmpty() && !serverUniqueID.equals("null") && numLobby != null
				&& !numLobby.isEmpty() && !numLobby.equals("null")) {
//			System.out.print("serverUniqueID " + serverUniqueID);
//			System.out.println("        numLobby " + numLobby);
			Player player = game.retrievePlayer(serverUniqueID, numLobby);
			response.getWriter().append(game.retrieveMate(player));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

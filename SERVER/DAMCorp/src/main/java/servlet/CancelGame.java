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
 * Servlet implementation class CancelGame
 */
public class CancelGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Game game = new Game();
    /**
     * Default constructor. 
     */
    public CancelGame() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String serverUniqueID = request.getParameter("serverUniqueID");
		String numLobby = request.getParameter("numLobby");
		
		if (serverUniqueID != null && !serverUniqueID.isEmpty() && !serverUniqueID.equals("null") && numLobby != null
				&& !numLobby.isEmpty() && !numLobby.equals("null")) {
			game.cancelGame(serverUniqueID, numLobby);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import game.Game;
import game.entity.Invitation;
import game.entity.Player;

/**
 * Servlet implementation class NewInvitation
 */
public class NewInvitation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Game game = new Game();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewInvitation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String serverUniqueID0 = request.getParameter("serverUniqueID0");
		String numLobby0 = request.getParameter("numLobby0");
		String serverUniqueID1 = request.getParameter("serverUniqueID1");
		String numLobby1 = request.getParameter("numLobby1");
		// TODO Auto-generated method stub
		if (
				serverUniqueID0 != null && !serverUniqueID0.isEmpty() && !serverUniqueID0.equals("null") && numLobby0 != null
		&& !numLobby0.isEmpty() && !numLobby0.equals("null")
		&&  serverUniqueID1 != null && !serverUniqueID1.isEmpty() && !serverUniqueID1.equals("null") && numLobby1 != null
		&& !numLobby1.isEmpty() && !numLobby1.equals("null")
				
				) {
			Player player0 = game.retrievePlayer(request.getParameter("serverUniqueID0"),
					request.getParameter("numLobby0"));
			Player player1 = game.retrievePlayer(request.getParameter("serverUniqueID1"),
					request.getParameter("numLobby1"));

			if (player0 != null && player1 != null) {

				
				Invitation.sendInvitation(new Invitation(player0, player1, numLobby0, numLobby1));
			}
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

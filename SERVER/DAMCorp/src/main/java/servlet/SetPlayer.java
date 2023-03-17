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
 * Servlet implementation class SetPlayer
 */
public class SetPlayer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Game game = new Game();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetPlayer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String lobbyJoueurId = request.getParameter("lobbyJoueurId");
		String lobbyId = request.getParameter("lobbyId");
		if (lobbyJoueurId != null  && lobbyId!= null
				&& !lobbyJoueurId.isEmpty() && !lobbyId.isEmpty()
				&& !lobbyJoueurId.equalsIgnoreCase("null") && !lobbyId.equalsIgnoreCase("null")
				) {
			Player player = game.retrievePlayer(lobbyJoueurId, lobbyId);
			if(player!= null) {
				if (request.getParameter("x") != null) {
					player.setX(request.getParameter("x"));
				}
				if (request.getParameter("y") != null) {
					player.setY(request.getParameter("y"));
				}

				if (request.getParameter("boxWidth") != null) {
					player.setBoxWidth(request.getParameter("boxWidth"));
				}

				if (request.getParameter("boxHeight") != null) {
					player.setBoxHeight(request.getParameter("boxHeight"));
				}

				if (request.getParameter("uniqueID") != null) {
					player.setUniqueID(request.getParameter("uniqueID"));
				}

				if (request.getParameter("spriteColorInt") != null) {
					player.setSpriteColorInt(request.getParameter("spriteColorInt"));
				}

				if (request.getParameter("findRegion") != null) {
					player.setFindRegion(request.getParameter("findRegion"));
				}

				if (request.getParameter("textureAtlasPath") != null) {
					player.setTextureAtlasPath(request.getParameter("textureAtlasPath"));
				}
				if (request.getParameter("scale") != null) {
					player.setScale(request.getParameter("scale"));
				}
			}
			
		} else {
			System.out.println("NewPlayer - No available place");
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

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
 * Servlet implementation class RetrievePlayer
 */
public class RetrievePlayer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Game game = new Game();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetrievePlayer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		if( request.getParameter("serverUniqueID")!=null) {
			Player player =game.retrievePlayer(request.getParameter("serverUniqueID"),request.getParameter("numLobby"));
			
			if(player!= null) {
				 response.getWriter().append(player.getX());
				 response.getWriter().append(";"+player.getY());
				 response.getWriter().append(";"+player.getBoxWidth());
				 response.getWriter().append(";"+player.getBoxHeight());
				 response.getWriter().append(";"+player.getUniqueID());
				 response.getWriter().append(";"+player.getServerUniqueID());
//				 response.getWriter().append(";"+player.getSpriteColorInt());
				 response.getWriter().append(";"+player.getFindRegion());
				 response.getWriter().append(";"+player.getTextureAtlasPath());
				 response.getWriter().append(";"+player.getScale());
				 response.getWriter().append(";"+player.getNumLobby());
				 
				 System.out.println();
				 System.out.println(player.getServerUniqueID());
				 System.out.println(player.getX());
				 System.out.println(player.getY());
				 System.out.println(player.getBoxWidth());
				 System.out.println(player.getBoxHeight());
				 System.out.println(player.getUniqueID());
				 System.out.println(player.getSpriteColorInt());
				 System.out.println(player.getFindRegion());
				 System.out.println(player.getTextureAtlasPath());
				 System.out.println(player.getScale());
				 System.out.println(player.getNumLobby());
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

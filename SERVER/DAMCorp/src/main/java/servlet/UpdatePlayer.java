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
 * Servlet implementation class UpdatePlayer
 */
public class UpdatePlayer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Game game = new Game();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePlayer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Player player =game.retrievePlayer(request.getParameter("serverUniqueID"));
		if(player != null) {
			if( request.getParameter("x")!=null) {
				player.setX( request.getParameter("x"));	
			}
			if( request.getParameter("y")!=null) {
				player.setY(request.getParameter("y"));	
			}
			if( request.getParameter("findRegion")!=null ) {
				player.setFindRegion(request.getParameter("findRegion"));	
			}
			player.setLastUpdate(System.currentTimeMillis());
//			System.out.println("player.x " + player.getX());
//			System.out.println("player.y " + player.getY());
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

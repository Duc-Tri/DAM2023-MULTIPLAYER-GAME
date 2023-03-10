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
 * Servlet implementation class RetrieveUpdatePlayers
 */
public class RetrieveUpdateMates extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Game game = new Game();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetrieveUpdateMates() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());

		// TODO Auto-generated method stub

		String serverUniqueID = request.getParameter("serverUniqueID");
		//		System.out.println("RetrieveUpdatePlayer =================== " + serverUniqueID);

		if (serverUniqueID != null && !serverUniqueID.isEmpty() && !serverUniqueID.equals("null")) {
			Player player = game.retrievePlayer(request.getParameter("serverUniqueID"));
			int cpt=0;
			if (player != null) {
				player.setLastUpdate(System.currentTimeMillis());
				for(int i=0; i < game.getPlayers().length; i++ ) {
					if(cpt>0) {
						response.getWriter().append("/");
					}
					response.getWriter().append(player.getX());
					response.getWriter().append(";" + player.getY());
					response.getWriter().append(";" + player.getFindRegion());


					cpt++;
				}

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

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		if( request.getParameter("serverUniqueID")!=null) {
//			System.out.println("RetrieveMate  " );
			Player player =game.retrievePlayer(request.getParameter("serverUniqueID"));
//			System.out.println("RetrieveMate  "+player );
			response.getWriter().append(game.retrieveMate(player));
//		System.out.println("response " + response.toString());
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

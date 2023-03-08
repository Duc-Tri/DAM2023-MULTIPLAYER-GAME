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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());

		String serverUniqueID = request.getParameter("serverUniqueID");
//		System.out.println("UpdatePlayer =================== " + serverUniqueID);

		if (serverUniqueID != null && !serverUniqueID.isEmpty() && !serverUniqueID.equals("null")) {

			Player player = game.retrievePlayer(request.getParameter("serverUniqueID"));

			if (request.getParameter("x") != null) {
				player.setX(request.getParameter("x"));
			}
			if (request.getParameter("y") != null) {
				player.setY(request.getParameter("y"));
			}

//		if( request.getParameter("boxWidth")!=null) {
//			player.setBoxWidth(request.getParameter("boxWidth"));	
//		}
//		
//		if( request.getParameter("boxHeight")!=null) {
//			player.setBoxHeight(request.getParameter("boxHeight"));	
//		}
//		
//		if( request.getParameter("uniqueID")!=null) {
//			player.setUniqueID(request.getParameter("uniqueID"));	
//		}
//		
//		if( request.getParameter("spriteColorInt")!=null) {
//			player.setSpriteColorInt(request.getParameter("spriteColorInt"));	
//		}
//		
			if (request.getParameter("findRegion") != null) {
				player.setFindRegion(request.getParameter("findRegion"));
			}

			
//			System.out.println("UpdatePlayer ##### " + player.getServerUniqueID() + " * x=" + player.getX() + " * y="
//					+ player.getY() + " * " + player.getFindRegion());
			

//		if( request.getParameter("textureAtlasPath")!=null) {
//			player.setTextureAtlasPath(request.getParameter("textureAtlasPath"));	
//		}
//		if( request.getParameter("scale")!=null) {
//			player.setScale(request.getParameter("scale"));	
//		}
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

package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import game.Game;
import game.entity.Player;

/**
 * Servlet implementation class NewPlayer
 */
public class NewPlayer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Game game = new Game();

//    private float x;
//    private float y;
//    private float boxWidth;
//    private float boxHeight;
//    public String uniqueID;
//    public String serverUniqueID;
//    private float spriteColorInt;
//    private String findRegion;
//    private String textureAtlasPath;
//    private float scale;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewPlayer() {
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

		if (game.possibleToScribe()) {
			
			System.out.println("Le GetID : "+game.getId());
			
			Player player = new Player(request.getParameter("uniqueID"), "" + game.getNextId());

			System.out.println("NewPlayer "+game+" =================== " + player.getServerUniqueID());

			

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

			game.addPlayer(player);

			
			response.getWriter().append(player.getServerUniqueID());
			response.getWriter().append(";"+player.getNumLobby());
			
			
			
			
			
			System.out.println("NewPlayer ========== " + player.getServerUniqueID() + " * " + player.getX() + " * "
					+ player.getY() + " * " + player.getBoxWidth() + " * " + player.getBoxHeight() + " * "
					+ player.getUniqueID() + " * " + player.getSpriteColorInt() + " * " + player.getFindRegion() + " * "
					+ player.getTextureAtlasPath() + " * " + player.getScale() + " * " + player.getNumLobby());
		} else {
			System.out.println("NewPlayer - No available place");
		}

		System.out.println("NewPlayer - doGet");

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

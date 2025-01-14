package servlets;

import java.io.IOException;

import dao.DAOLoginRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

// CONTROLLER
@WebServlet(urlPatterns = { "/principal/ServeletLogin", "/ServeletLogin" }) // Mapemaneto da URL
public class ServeletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();

	public ServeletLogin() {

	}

	/* Recebe os dados pela URL em parametros */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String acao = request.getParameter("acao");
		
		// FINALIZAR A SESS�O DO USU�RIO 
		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			request.getSession().invalidate();
			
			// REDIRECIONAR O USUARIO AP�S FAZER O LOGOUT
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			redirecionar.forward(request, response);
		}
			
		
	}

	/* Recebe os dados enviados por um formul�rio */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// RECEBE OS PARAMETROS DA WEB E IMPRIME NA TELA

		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");

		try {
			// SE O LOGIN E A SENHA N�O FOREM VAZIAS OU NULAS ELE CRIA UM NOVO LOGIN
			if (!login.isBlank() && !senha.isBlank()) {
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setLogin(login);
				modelLogin.setSenha(senha);
				
				// SIMULANDO O LOGIN

				if (daoLoginRepository.validarAutenticacao(modelLogin)) {
					// INFORMANDO UMA SESS�O ABERTA DE USUARIO
					request.getSession().setAttribute("usuario", modelLogin.getLogin());
					
					
					// SE A PAGINA N�O FOR NULA VAI SER SETADA COMO A PAGINA PRINCIPAL
					if (url == null || url.equals("null")) {
						url = "/principal/principal.jsp";
					}
					// REDIRECIONAR
					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);
				} else {
					// EM CASO DE ERRO NA AUTENTICA��O RETORNAR PRA MESMA P�GINA
					RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
					// MENSAGEM DE ERRO
					request.setAttribute("msg", "Login ou senha incorreta!");
					// FAZER O REDIRECIONAMENTO
					redirecionar.forward(request, response);
				}
			} else {
				RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "Login ou senha incorreta!");
				redirecionar.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}
		
}

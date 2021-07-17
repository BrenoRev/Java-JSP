package servlets;

import java.io.IOException;
import java.sql.SQLException;

import dao.DAOLoginRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

// CONTROLLER
@WebServlet(urlPatterns= {"/principal/ServeletLogin", "/ServeletLogin"}) // Mapemaneto da URL
public class ServeletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();

    public ServeletLogin() {
       
    }
    /* Recebe os dados pela URL em parametros */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	/* Recebe os dados enviados por um formul�rio */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// RECEBE OS PARAMETROS DA WEB E IMPRIME NA TELA
		
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");
		
		// SE O LOGIN E A SENHA N�O FOREM VAZIAS OU NULAS ELE CRIA UM NOVO LOGIN
		if(!login.isBlank() && !senha.isBlank()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			
			// SIMULANDO O LOGIN
			try {
				if(daoLoginRepository.validarAutenticacao(modelLogin))
					{
					// INFORMANDO UMA SESS�O ABERTA DE USUARIO
						request.getSession().setAttribute("usuario", modelLogin.getLogin());
						
						// SE A PAGINA N�O FOR NULA VAI SER SETADA COMO A PAGINA PRINCIPAL
						if(url == null || url.equals("null")) {
							url = "/principal/principal.jsp";
						}
						
						// REDIRECIONAR
						RequestDispatcher redirecionar = request.getRequestDispatcher(url);
						redirecionar.forward(request, response);
					}else {
					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Informe o login e senha correatmente!");
					redirecionar.forward(request, response);
				}
			} catch (SQLException | ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			// EM CASO DE ERRO NA AUTENTICA��O RETORNAR PRA MESMA P�GINA
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			// MENSAGEM DE ERRO
			request.setAttribute("msg", "Informe o login e senha correatmente!");
			// FAZER O REDIRECIONAMENTO
			redirecionar.forward(request, response);
		}
		
	}

}

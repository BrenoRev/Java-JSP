package servlets;

import java.io.IOException;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

// CLASSE RESPONS�VEL POR CONTROLAR O CADASTRO DE NOVOS USUARIOS
@WebServlet("/ServeletRegistroInicialController")
public class ServeletRegistroInicialController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
    public ServeletRegistroInicialController() {
        // TODO Auto-generated constructor stub
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doPost(request,response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String msg;
		ModelLogin modelLogin = new ModelLogin();
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String classe = request.getParameter("classe");
		String login = request.getParameter("login");
		
		
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setClasse(classe);
		modelLogin.setSenha(senha);
		modelLogin.setLogin(login);
		
		if(daoUsuarioRepository.validarLogin(modelLogin.getLogin())) {
			msg = "J� Existe um usu�rio com o mesmo login!";
			request.setAttribute("msg", msg);
			RequestDispatcher redireciona = request.getRequestDispatcher("registroinicial.jsp");
			redireciona.forward(request, response);
		}else {
				msg = "Usuario criado com sucesso";
				daoUsuarioRepository.gravarUsuario(modelLogin);
				request.setAttribute("msg", msg);
				RequestDispatcher redireciona = request.getRequestDispatcher("index.jsp");
				redireciona.forward(request, response);
		}
		
	
	}catch(Exception e) {
		e.printStackTrace();
		RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
		request.setAttribute("msg", e.getMessage());
		redirecionar.forward(request, response);
	}
	}
}

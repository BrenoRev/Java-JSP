package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

// INTERCEPTA TODAS AS REQUISI��ES QUE VIEREM DO PROJETO OU DO MAPEAMENTO
@WebFilter(urlPatterns = {"/principal/*"})
public class FilterAutenticacao implements Filter {
    public FilterAutenticacao() {
        // TODO Auto-generated constructor stub
    }

    // ENCERRA OS PROCESSOS QUANDO O SERVIDOR � PARADO
	public void destroy() {
	}
	
	// INTERCEPTA AS REQUISICOES E AS RESPOSTAS
	// TUDO QUE FIZER NO SISTEMA VAI PASSAR POR ESSE M�TODO
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String usuarioLogado = (String) session.getAttribute("usuario");
		String urlParaAutenticar = req.getServletPath(); // URL QUE EST� SENDO ACESSADA
		
		// VALIDAR SE EST� LOGADO SEN�O REDIRECIONA PARA A TELA DE LOGIN
		// SE O USUARIO N�O ESTIVER LOGADO VAI REDIRECIONAR PARA O INDEX E MOSTRAR A MENSAGEM
		if(usuarioLogado == null || usuarioLogado.equals("null") && 
				!urlParaAutenticar.equalsIgnoreCase("/principal/ServeletLogin")) {
			RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
			request.setAttribute("msg", "Realize o login!");
			redireciona.forward(request, response);
			return; // PARA A EXECU��O E REDIRECIONA PARA O LOGIN
		}else {
			chain.doFilter(request, response);
		}
		
		
	}

	// � EXECUTADO QUANDO INICIA O SISTEMA
	public void init(FilterConfig fConfig) throws ServletException {
	}

}

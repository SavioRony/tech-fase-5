package br.com.fiap.apigateway.jwt;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;
import java.util.Base64;
import io.jsonwebtoken.Jwts;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import javax.servlet.http.HttpServletRequest;

public class JwtTokenFilter extends ZuulFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE; // Filtrar antes de encaminhar a solicitação para o microserviço de destino
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1; // Definir a ordem do filtro
    }

    @Override
    public boolean shouldFilter() {
        return true; // Filtrar todas as solicitações
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String token = extractToken(request);
        if(token != null){
            String email = getEmailFromToken(token);
            ctx.addZuulRequestHeader("X-User-Email", email);
        }

        return null;
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String getEmailFromToken(String token) {
        if(token != null){
            String [] parts = token.split("\\.");
            if(parts.length == 3){
                String body64 = parts[1];
                byte[] bodyByte = Base64.getDecoder().decode(body64);
                String body = new String( bodyByte);
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    return jsonObject.getString("user_name");
                } catch (JSONException e) {
                    return null;
                }
            }
        }
        return null;
    }
}

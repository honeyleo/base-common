package cn.lfy.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.safety.Whitelist;
import org.owasp.esapi.ESAPI;

public class SecurityRequestWrapper extends HttpServletRequestWrapper {

	private final static Whitelist WHITELIST = Whitelist.relaxed();

	private final static OutputSettings OUTPUTSETTINGS = new OutputSettings().prettyPrint( false );

	static {
		WHITELIST.addTags( "embed", "object", "param", "span", "div", "img", "font" );
		WHITELIST.addAttributes( ":all", "style", "class", "id", "name", "color");
		WHITELIST.addAttributes( "object", "width", "height", "classid", "codebase" );
		WHITELIST.addAttributes( "param", "name", "value" );
		WHITELIST.addAttributes( "embed", "src", "quality", "width", "height", "allowFullScreen",
				"allowScriptAccess", "flashvars", "name", "type", "pluginspage" );
	}

	public SecurityRequestWrapper( HttpServletRequest servletRequest ) {
		super( servletRequest );
	}

	@Override
	public String[] getParameterValues( String parameter ) {
		String[] values = super.getParameterValues( parameter );
		if( null == values ) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[ count ];
		for( int i = 0; i < count; i++ ) {
			encodedValues[ i ] = filterValue( values[ i ] );
		}
		return encodedValues;
	}

	@Override
	public String getParameter( String parameter ) {
		String value = super.getParameter( parameter );
		return filterValue( value );
	}

	@Override
	public String getHeader( String name ) {
		String value = super.getHeader( name );
		return filterValue( value );
	}

	private String filterValue( String value ) {
		if( null != value ) {
			// avoid encoded attacks.
			value = ESAPI.encoder().canonicalize( value );

			// Avoid null characters
			value = value.replaceAll( "\0", "" );

			// Clean out HTML
			value = Jsoup.clean( value, "", WHITELIST, OUTPUTSETTINGS );
		}
		return value;
	}
}
package com.example.simpleCalculator.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.example.simpleCalculator.client.ConvertService;
import com.example.simpleCalculator.client.model.ConversionClient;
import com.example.simpleCalculator.server.model.Conversion;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ConvertServiceImpl extends RemoteServiceServlet implements ConvertService {

	private static final long serialVersionUID = 4276360013759733550L;
	private static final PersistenceManagerFactory PMF =
		      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	public String convertToBinary(String decimal) throws IllegalStateException {
		Conversion conv = new Conversion(decimal);

		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(conv);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			pm.close();
		}

		return conv.getBinary();
	}
	
	public List<ConversionClient> getHistory() throws IllegalArgumentException {
		PersistenceManager pm = getPersistenceManager();
		List<ConversionClient> history = new ArrayList<ConversionClient>();
		try {
			String query = "select from " + Conversion.class.getName() + " order by timestamp";
			@SuppressWarnings("unchecked")
			List<Conversion> list = (List<Conversion>) pm.newQuery(query).execute();
			
			SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
			for(Conversion conv : list) {
				history.add(new ConversionClient(format.format(conv.getTimestamp()), conv.getDecimal(), conv.getBinary()));
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			pm.close();
		}

		return history;
	}
	
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}
}

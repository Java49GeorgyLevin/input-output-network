package telran.net;

public class CalculatorProtocol implements ApplProtocol {

	@Override
	public Response getResponse(Request request) {
		Double[] datas = null;
		Response response;
		try {
			datas = (Double[]) request.requestData();			
		} catch (Exception e) {
			new Response(ResponseCode.WRONG_DATA, e.getMessage());
		};
		if(datas.length != 2) {
			response = new Response(ResponseCode.WRONG_DATA, request.requestData());
		} else { 
			response = switch(request.requestType()) {
			case "add" -> new Response(ResponseCode.OK, datas[0] + datas[1]);
			case "minus" ->  new Response(ResponseCode.OK, datas[0] - datas[1]);
			case "multiply" ->  new Response(ResponseCode.OK, datas[0] * datas[1]);
			case "divide" ->  new Response(ResponseCode.OK, datas[0] / datas[1]);		
			default -> new Response(ResponseCode.WRONG_TYPE, request.requestType());		
		};
		};
		return response;
	}		
	
}

	

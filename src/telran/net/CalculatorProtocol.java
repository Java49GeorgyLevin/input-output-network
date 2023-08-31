package telran.net;


public class CalculatorProtocol implements ApplProtocol {

	@Override
	public Response getResponse(Request request) {
		Response response = null;
		
		try {
			double[] operands = (double[])request.requestData();
			String requestType = request.requestType();
			double res = switch(requestType) {
			case "add" -> operands[0] + operands[1];
			case "multiply" -> operands[0] * operands[1];
			case "divide" -> operands[0] / operands[1];
			case "minus" -> operands[0] - operands[1];
			default -> Double.NaN;
			};
			response = Double.isNaN(res) ?
					new Response(ResponseCode.WRONG_TYPE, requestType + " is wrong request type"):
						new Response(ResponseCode.OK, res);
			return response;
		} catch (Exception e) {
			return new Response(ResponseCode.WRONG_DATA, e.getMessage());
		}
	}

	
}

package com.ticketbooking.common;

/**
 * Utility class for returning results
 */
public class ResultUtil<T> {

  /**
   * Abstract class, stores the result
   */
  private final ResultMessage<T> responseMessage;
  /**
   * Success response
   */
  private static final Integer SUCCESS_CODE = 200;

  /**
   * Constructor, sets default values for the response
   */
  public ResultUtil() {
    responseMessage = new ResultMessage<>();
    responseMessage.setSuccess(true);
    responseMessage.setMessage("success");
    responseMessage.setCode(SUCCESS_CODE);
  }

  /**
   * Returns the data
   *
   * @param t The data type
   * @return The response message
   */
  public ResultMessage<T> setData(T t) {
    this.responseMessage.setResult(t);
    return this.responseMessage;
  }

  /**
   * Returns the success message
   *
   * @param resultCode The result code
   * @return The success message
   */
  public ResultMessage<T> setSuccessMsg(ResultCode resultCode) {
    this.responseMessage.setSuccess(true);
    this.responseMessage.setMessage(resultCode.message());
    this.responseMessage.setCode(resultCode.code());
    return this.responseMessage;

  }

  /**
   * Static method, returns the result
   *
   * @param t   The data type
   * @param <T> The data type
   * @return The response message
   */
  public static <T> ResultMessage<T> data(T t) {
    return new ResultUtil<T>().setData(t);
  }

  /**
   * Returns success
   *
   * @param responseStatusCode The response status code
   * @return The response message
   */
  public static <T> ResultMessage<T> success(ResultCode responseStatusCode) {
    return new ResultUtil<T>().setSuccessMsg(responseStatusCode);
  }

  /**
   * Returns success
   *
   * @return The response message
   */
  public static <T> ResultMessage<T> success() {
    return new ResultUtil<T>().setSuccessMsg(ResultCode.SUCCESS);
  }

  /**
   * Returns error
   *
   * @param responseStatusCode The response status code
   * @return The response message
   */
  public static <T> ResultMessage<T> error(ResultCode responseStatusCode) {
    return new ResultUtil<T>().setErrorMsg(responseStatusCode);
  }

  /**
   * Returns error
   *
   * @param code The response status code
   * @param msg  The response message
   * @return The response message
   */
  public static <T> ResultMessage<T> error(Integer code, String msg) {
    return new ResultUtil<T>().setErrorMsg(code, msg);
  }

  /**
   * Error, add status code
   *
   * @param resultCode Result code
   * @return Response message
   */
  public ResultMessage<T> setErrorMsg(ResultCode resultCode) {
    this.responseMessage.setSuccess(false);
    this.responseMessage.setMessage(resultCode.message());
    this.responseMessage.setCode(resultCode.code());
    return this.responseMessage;
  }

  /**
   * Error, add status code
   *
   * @param code The response status code
   * @param msg  The response message
   * @return The response message
   */
  public ResultMessage<T> setErrorMsg(Integer code, String msg) {
    this.responseMessage.setSuccess(false);
    this.responseMessage.setMessage(msg);
    this.responseMessage.setCode(code);
    return this.responseMessage;
  }
}

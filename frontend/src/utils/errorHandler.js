export const handleApiError = (error) => {
  if (error.response) {
    const {status, data} = error.response;

    switch (status) {
      case 400:
        return data.message || '잘못된 요청입니다.';
      case 401:
        return '인증이 필요합니다.';
      case 403:
        return '권한이 없습니다.';
      case 404:
        return '요청한 리소스를 찾을 수 없습니다.';
      case 422:
        return data.errors ? Object.values(data.errors).flat().join(', ') : '입력값이 올바르지 않습니다.';
      case 500:
        return '서버 오류가 발생했습니다.';
      default:
        return data.message || '알 수 없는 오류가 발생했습니다.';
    }
  } else if (error.request) {
    // 요청은 보냈지만 응답을 받지 못함
    return '서버와의 연결에 실패했습니다.';
  } else {
    // 요청 설정 중에 에러 발생
    return '요청 처리 중 오류가 발생했습니다.';
  }
}
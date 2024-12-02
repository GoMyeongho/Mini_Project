import React, {useNavigate} from "react-router-dom";
import axios from "axios";

const TestLogin = () => {
  const navigate = useNavigate();

  const homeButtonClick = async () => {
    try {
      // Spring Boot에 데이터 전송 및 URL 반환받기
      const response = await axios.post("/navigate/home");
      const redirectUrl = response.data.redirectUrl;
      console.log("Redirect URL : ", redirectUrl);
      // 반환받은 URL로 화면 이동
      navigate(redirectUrl);
    } catch (error) {
      console.error("Error during navigation:", error);
      alert("페이지 이동 중 오류가 발생했습니다.");
    }
  };
  return (
    <>
      <h1>테스트 로그인 화면 입니다.</h1>
      <button className="login" onClick={homeButtonClick}>
        홈 화면 이동
      </button>
    </>
  );
};
export default TestLogin;

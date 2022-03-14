import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface ChatService {

	// 입장
	int enter(String nickname, DataOutputStream outputStream);

	// 채팅
	void chat(String message);

	// 퇴장
	void quit(DataOutputStream ouputStream);
}

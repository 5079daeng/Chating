import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatThread extends Thread {
	private final Socket socket;
	private final ChatService service;

	public ChatThread(Socket socket, ChatService service) {
		this.socket = socket;
		this.service = service;
	}

	public void run() {
		try {
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			String command;
			String command2;
			while (true) {

				command = dis.readUTF();

				if (command.equals("/ENTER")) {
					command2 = dis.readUTF();

					int nickNameCheck = service.enter(command2, dos);
					if (nickNameCheck == 1) {
						dos.writeUTF("이미 등록된 아이디입니다");
					} else {
						dos.writeUTF(command2 + "사용자가 입장하였습니다");
					}
				} else if (command.equals("/CHAT")) {

					String message = (dis.readUTF());
					// 쳇 보내고 채팅 메세지도 보내는 것
					service.chat(message);
				} else if (command.equals("/QUIT")) {
					service.quit(dos);
					break;

				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}

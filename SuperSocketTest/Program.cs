using SuperSocket.SocketBase;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SuperSocketTest
{
    class Program
    {
        static void Main(string[] args)
        {
            /*
            byte[] b = System.Text.Encoding.UTF8.GetBytes("咸鱼");
            for (int i = 0; i < b.Length; i++)
            {
                Console.WriteLine("b:" + b[i] + "\t");
            }
            */
            var appServer = new AppServer();

            if (!appServer.Setup(1994))
            {
                Console.WriteLine("端口1994已经被占用");
                Console.ReadKey();
                return;
            }

            Console.WriteLine();

            if (!appServer.Start())
            {
                Console.WriteLine("服务启动失败");
                Console.ReadKey();
                return;
            }
            
            appServer.NewSessionConnected += appServer_NewSessionConnected;
            appServer.NewRequestReceived += appServer_NewRequestReceived;
            appServer.SessionClosed += appServer_SessionClosed;

            Console.WriteLine("服务启动成功");

            while (Console.ReadKey().KeyChar != 'q')
            {
                Console.WriteLine();
                continue;
            }

            appServer.Stop();

            Console.WriteLine("服务关闭");
            Console.ReadKey();
        }

        /// <summary>
        /// 客户端连接的事件
        /// </summary>
        /// <param name="session"></param>
        static void appServer_NewSessionConnected(AppSession session)
        {
            session.Charset = System.Text.Encoding.UTF8;
            Console.WriteLine("客户端连接成功,客户端ip:" + session.RemoteEndPoint + " connect");
        }

        /// <summary>
        /// 接收到客户端发送的请求
        /// </summary>
        /// <param name="session"></param>
        /// <param name="requestInfo"></param>
        static void appServer_NewRequestReceived(AppSession session, SuperSocket.SocketBase.Protocol.StringRequestInfo requestInfo)
        {
            var key = requestInfo.Key;
            if (key != null) { key = key.Replace("\r\n", "").Replace("\n", ""); }
            var body = requestInfo.Body;
            if (body != null) { body = body.Replace("\r\n", "").Replace("\n", ""); }
            switch (key)
            {
                case "1":
                    Console.WriteLine("hello word");
                    break;
                case "2":
                    Console.WriteLine("LOL");
                    break;
                case "3":
                    body = Base64Util.decode(body);
                    Console.WriteLine("case3:" + body);
                    string txt = "key:" + key + " body:" + body;
                    txt = Base64Util.encode(txt);
                    session.Send(txt);
                    break;
                default:
                    Console.WriteLine("key:" + key + " body:" + body);

                    break;
            }
            // Console.WriteLine("session id:" + session.SessionID);
        }

        /// <summary>
        /// 客户端关闭连接
        /// </summary>
        /// <param name="session"></param>
        /// <param name="reason"></param>
        static void appServer_SessionClosed(AppSession session, CloseReason reason)
        {
            session.Close();
            Console.WriteLine("与客户端的连接被关闭,关闭原因是:" + reason.ToString());
        }
    }
}

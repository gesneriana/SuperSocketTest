using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SuperSocketTest
{
    /// <summary>
    /// 处理Java字符串乱码的问题
    /// </summary>
    public class Base64Util
    {
        /// <summary>
        /// 字符串默认为UTF-8编码
        /// </summary>
        /// <param name="str"></param>
        /// <returns></returns>
        public static string encode(string str)
        {
            var b = System.Text.Encoding.UTF8.GetBytes(str);
            return Convert.ToBase64String(b);
        }

        /// <summary>
        /// 字符串编码需要指定,转换为base64
        /// </summary>
        /// <param name="str"></param>
        /// <param name="enc"></param>
        /// <returns></returns>
        public string Encode(string str, System.Text.Encoding enc)
        {
            var b = enc.GetBytes(str);
            return Convert.ToBase64String(b);
        }

        /// <summary>
        /// 解码, 默认为UTF-8格式, 与ASCII编码没区别, 因为都是大小写字母
        /// </summary>
        /// <param name="str"></param>
        /// <returns></returns>
        public static string decode(string str)
        {
            byte[] outputb = Convert.FromBase64String(str);
            return System.Text.Encoding.UTF8.GetString(outputb);
        }
    }
}

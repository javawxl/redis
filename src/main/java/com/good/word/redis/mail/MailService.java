package com.good.word.redis.mail;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Component
public class MailService {
    @Value("${spring.mail.properties.mail.from}")
    private final String from = null;
    private final JavaMailSender sender;
    private final FreeMarkerConfigurer configurer;

    public MailService(JavaMailSender sender, FreeMarkerConfigurer configurer) {
        this.sender = sender;
        this.configurer = configurer;
    }

    /**
     *
     * @param recipient 接收人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendSimpleMail(String recipient, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(content);
        sender.send(message);
    }

    /**
     *
     * @param recipient 接收人邮箱
     * @param subject 邮件主题
     * @param content 富文本邮件内容
     * @throws MessagingException
     */
    public void sendHtmlMail(String recipient, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(content, true);
        sender.send(mimeMessage);
    }

    /**
     *
     * @param recipient 接收人邮箱
     * @param subject 邮件主题
     * @param tplFile freemarker模版文件名
     * @param data 数据
     */
    public void sendTemplateMail(String recipient, String subject, String tplFile, Map<String, Object> data)
            throws IOException, TemplateException, MessagingException {
        Template template = configurer.getConfiguration().getTemplate(tplFile);
        String templateHtml = FreeMarkerTemplateUtils.processTemplateIntoString(template,data);
        this.sendHtmlMail(recipient, subject, templateHtml);
    }

    /**
     *
     * @param recipient 接收人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param filePath 附件路径
     * @throws MessagingException
     */
    public void sendAttachmentsMail(String recipient, String subject, String content, String filePath) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(content, true);
        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);
        sender.send(message);
    }

    /**
     *
     * @param recipient
     * @param subject
     * @param tplFile
     * @param data
     * @param filePath
     * @throws MessagingException
     * @throws IOException
     * @throws TemplateException
     */
    public void sendAttachmentsMail(String recipient, String subject, String tplFile, Map<String, Object> data, String filePath) throws MessagingException, IOException, TemplateException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(recipient);
        helper.setSubject(subject);
        Template template = configurer.getConfiguration().getTemplate(tplFile);
        String templateHtml = FreeMarkerTemplateUtils.processTemplateIntoString(template,data);
        helper.setText(templateHtml, true);
        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);
        sender.send(message);
    }
}
